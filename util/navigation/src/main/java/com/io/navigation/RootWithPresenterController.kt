package com.io.navigation

import android.util.ArrayMap
import android.util.SparseArray
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import java.util.*

class RootWithPresenterController(
    private var presenterFactory: PresenterFactory,
    rootControllerType: RootControllerType = RootControllerType.Root,
    backgroundColor: Color,
): RootController(rootControllerType, backgroundColor) {

    private val currentScreenKey: String
         get() = currentScreen.value.screen.key

    private val presenterExitsMap = hashMapOf<Class<out UIPresenter>, UIPresenter>()
    private val screenWithPresenterMap = hashMapOf<String, MutableList<Class<out UIPresenter>>>()
    private val screensKeys = Stack<ScreenInfo>()

    fun launch(
        screen: String,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null,
        presenterFactory: (() -> PresenterFactory)? = null
    ) {

        val screenInfo = ScreenInfo(
            screen = screen,
            presenterFactory = presenterFactory?.invoke()
        )

        screensKeys.push(screenInfo)
        screenWithPresenterMap[screen] = mutableListOf()

        updateFactoryForPresenter(screenInfo.presenterFactory)

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

    override fun popBackStack() {
        removeLastScreenWithPresenters()
        super.popBackStack()
    }

    fun backToScreenAndRemovePresenters(screen: String){
        var topScreen = currentScreenKey
        while (topScreen != screen){
            removeLastScreenWithPresenters()
            val screenInfo = screensKeys.peek() ?: throw RuntimeException("Not found screen")

            topScreen = screenInfo.screen
            updateFactoryForPresenter(screenInfo.presenterFactory)
        }

        backToScreen(screen)
    }

    private fun updateFactoryForPresenter(factory: PresenterFactory?){
        if (factory == null) return
        presenterFactory = factory
    }

    private fun removeLastScreenWithPresenters(){
        val screen = screensKeys.pop()
        screenWithPresenterMap.remove(screen.screen)?.forEach { clazzPresenter ->
            presenterExitsMap.remove(clazzPresenter)?.clear()
        }
    }

    fun <P : UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>
    ): P {
        if (presenterExitsMap.containsKey(clazz)){
            @Suppress("UNCHECKED_CAST")
            return presenterExitsMap[clazz]!! as P
        } else {
            val presenter = presenterFactory.create<P>(clazz)
            screenWithPresenterMap[currentScreenKey]?.apply { add(clazz) }
            presenterExitsMap[clazz] = presenter
            return presenter
        }
    }
}