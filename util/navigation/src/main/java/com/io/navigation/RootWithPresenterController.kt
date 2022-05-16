package com.io.navigation

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
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


        updateFactoryForPresenter(presenterFactory?.invoke())

        launch(
            screen = screen,
            startScreen = startScreen,
            startTabPosition = startTabPosition,
            params = params,
            animationType = animationType,
            launchFlag = launchFlag,
            deepLink = false
        )

        val screenInfo = ScreenInfo(
            screen = currentScreenKey,
            presenterFactory = presenterFactory
        )

        screensKeys.push(screenInfo)
        screenWithPresenterMap[screenInfo.screen] = mutableListOf()
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

    override fun popBackStack() {
        removeLastScreenWithPresenters()
        super.popBackStack()
    }

    fun backToScreenAndRemovePresenters(screen: String){
        var topScreen = currentScreenKey
        while (topScreen != screen){
            removeLastScreenWithPresenters()
            val screenInfo = if (screensKeys.size == 0) throw RuntimeException("Not found screen")
                                else screensKeys.peek()

            topScreen = screenInfo.screen
        }

        backToScreen(screen)
    }

    private fun updateFactoryForPresenter(factory: PresenterFactory?){
        if (factory == null) return
        if (currentPresenterFactory == factory) return
        println("Presenter updateFactory")
        currentPresenterFactory = factory
    }

    private fun removeLastScreenWithPresenters(){
        val screen = screensKeys.pop()
        println("Presenter remove ${screen.screen}")
        screenWithPresenterMap.remove(screen.screen)?.forEach { clazzPresenter ->
            presenterExitsMap.remove(clazzPresenter)?.clear()
        }
        updateFactoryForPresenter(screen.presenterFactory?.invoke())
    }

    fun <P : UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>
    ): P {
        if (presenterExitsMap.containsKey(clazz)){
            println("Presenter get ${clazz.simpleName}")
            @Suppress("UNCHECKED_CAST")
            return presenterExitsMap[clazz]!! as P
        } else {
            val presenter = currentPresenterFactory.create<P>(clazz)
            screenWithPresenterMap[currentScreenKey]?.apply {
                add(clazz)
            }
            presenterExitsMap[clazz] = presenter
            println("Presenter add $currentScreenKey ${presenter::class.java.simpleName}")
            return presenter
        }
    }
}