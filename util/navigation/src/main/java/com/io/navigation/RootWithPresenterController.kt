package com.io.navigation

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.screen.Screen
import java.util.*

class RootWithPresenterController(
    startPresenterFactory: () -> PresenterFactory = ::emptyPresenter,
    rootControllerType: RootControllerType = RootControllerType.Root,
    backgroundColor: Color = Color.White,
): RootController(rootControllerType, backgroundColor) {

    private val currentScreenKey: String
         get() = currentScreen.value.screen.key

    private val presenterExitsMap = hashMapOf<Class<out UIPresenter>, UIPresenter>()
    private val screenWithPresenterMap = hashMapOf<String, MutableList<Class<out UIPresenter>>>()
    private val screensKeys = Stack<ScreenInfo>()

    private var currentPresenterFactory: PresenterFactory = startPresenterFactory.invoke()

    init {
        val screenInfo = ScreenInfo(
            screen = "",
            presenterFactory = startPresenterFactory
        )

        screensKeys.push(screenInfo)
        screenWithPresenterMap[screenInfo.screen] = mutableListOf()
    }

    fun launch(
        screen: String,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null,
        presenterFactory: (() -> PresenterFactory)? = null
    ) {

        if (presenterFactory != null){

            val screenInfo = ScreenInfo(
                screen = currentScreenKey,
                presenterFactory = presenterFactory
            )

            screensKeys.push(screenInfo)

            updateFactoryForPresenter(presenterFactory.invoke())
        }

        launch(
            screen = screen,
            startScreen = startScreen,
            startTabPosition = startTabPosition,
            params = params,
            animationType = animationType,
            launchFlag = launchFlag,
            deepLink = false
        )
    }

    fun present(
        screen: String,
        startTabPosition: Int = 0,
        startScreen: String? = null,
        params: Any? = null,
        launchFlag: LaunchFlag? = null,
        presenterFactory: (() -> PresenterFactory)? = null
    ) {
        launch(
            screen = screen,
            startScreen = startScreen,
            startTabPosition = startTabPosition,
            params = params,
            animationType = defaultPresentationAnimation(),
            launchFlag = launchFlag,
            presenterFactory = presenterFactory
        )
    }

    fun push(
        screen: String,
        params: Any? = null,
        launchFlag: LaunchFlag? = null,
        presenterFactory: (() -> PresenterFactory)? = null
    ) {
        launch(
            screen = screen,
            startScreen = null,
            startTabPosition = 0,
            params = params,
            animationType = defaultPushAnimation(),
            launchFlag = launchFlag,
            presenterFactory = presenterFactory
        )
    }

    fun backToScreenWithPresenter(screen: String){
        stopWorkPresenter(currentScreenKey)
        backToScreen(screen)
        deleteUnnecessaryFactory()
    }

    override fun popBackStack() {
        stopWorkPresenter(currentScreenKey)
        super.popBackStack()
        removeLastScreenWithPresenters()
    }

    private fun deleteUnnecessaryFactory(){
        val backstack = getBackStack()

        var isFind = false
        while (!isFind){
            if (screensKeys.size == 1){
                val screenInfo = screensKeys.peek()
                updateFactoryForPresenter(screenInfo.presenterFactory())
                break
            }

            val screenInfo = screensKeys.pop()
            val currentScreen = backstack.findLast { it.key == screenInfo.screen }
            stopWorkPresenter(screenInfo.screen)

            if (currentScreen != null){
                updateFactoryForPresenter(screenInfo.presenterFactory())
                isFind = true
            }
        }
    }

    private fun getBackStack(): List<Screen>{
        val backstackField = RootController::class.java.getDeclaredField("_backstack")
        backstackField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return backstackField.get(this) as MutableList<Screen>
    }

    private fun updateFactoryForPresenter(factory: PresenterFactory?){
        if (factory == null) return
        if (currentPresenterFactory == factory) return
        currentPresenterFactory = factory
    }

    private fun stopWorkPresenter(screen: String){
        screenWithPresenterMap.remove(screen)?.forEach { clazzPresenter ->
            val presenter = presenterExitsMap.remove(clazzPresenter)
            presenter?.clear()
        }
    }

    private fun removeLastScreenWithPresenters(){
        val screen = screensKeys.peek()
        if (screen.screen == currentScreenKey || screensKeys.size == 1){
            updateFactoryForPresenter(screen.presenterFactory.invoke())
            if (screensKeys.size != 1){
                screensKeys.pop()
            }
        }
    }

    private fun checkOnNullPresenterMap(){
        if (screenWithPresenterMap[currentScreenKey] == null){
            screenWithPresenterMap[currentScreenKey] = mutableListOf()
        }
    }

    fun <P : UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>
    ): P {
        if (presenterExitsMap.containsKey(clazz)){
            @Suppress("UNCHECKED_CAST")
            return presenterExitsMap[clazz]!! as P
        } else {
            val presenter = currentPresenterFactory.create<P>(clazz)
            checkOnNullPresenterMap()
            screenWithPresenterMap[currentScreenKey]?.apply { add(clazz) }
            presenterExitsMap[clazz] = presenter
            return presenter
        }
    }
}