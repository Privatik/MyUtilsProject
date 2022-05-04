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
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher

class RootWithPresenterController(
    private var presenterFactory: PresenterFactory,
    rootControllerType: RootControllerType = RootControllerType.Root,
    backgroundColor: Color,
): RootController(rootControllerType, backgroundColor) {

    private val currentScreenKey: String
         get() = currentScreen.value.screen.key

    private val presenterExitsMap = hashMapOf<Class<out UIPresenter>, UIPresenter>()
    private val screenWithPresenterMap = hashMapOf<String, MutableList<Class<out UIPresenter>>>()
    private val screensKeys = mutableListOf<String>()

    private fun updateFactoryForPresenter(factory: PresenterFactory){
        presenterFactory = factory
    }

    fun push(
        screen: String,
        params: Any? = null,
        launchFlag: LaunchFlag? = null,
        presenterFactory: (() -> PresenterFactory)? = null
    ) {
        screensKeys.add(screen)
        screenWithPresenterMap[screen] = mutableListOf()

        if (presenterFactory != null){
            updateFactoryForPresenter(presenterFactory())
        }

        launch(
            screen = screen,
            startScreen = null,
            startTabPosition = 0,
            params = params,
            animationType = defaultPushAnimation(),
            launchFlag
        )
    }

    fun removeLastScreenWithPresenters(){
        val screen = screensKeys.removeLast()
        screenWithPresenterMap.remove(screen)?.forEach { clazzPresenter ->
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
            screenWithPresenterMap[currentScreenKey]?.apply {
                add(clazz)
            }
            presenterExitsMap[clazz] = presenter
            return presenter
        }
    }
}