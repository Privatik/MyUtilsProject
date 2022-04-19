package com.io.navigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.NavConfiguration
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher
import ru.alexgladkov.odyssey.core.screen.ScreenBundle
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class RootControllerFacade<Presenter: Any>(
    private val controller: RootController,
    private val _presenterStore: HashMap<String, KClass<out Presenter>>
) {
    private val store = HashMap<String, Presenter>()

    var parentRootController: RootController? = controller.parentRootController
    var onApplicationFinish: (() -> Unit)? = controller.onApplicationFinish
    var onScreenRemove: (ScreenBundle) -> Unit = controller.onScreenRemove

    var currentScreen: StateFlow<NavConfiguration> = controller.currentScreen

    val debugName: String?
        get() = controller.debugName

    fun getScreenRender(screenName: String?): RenderWithParams<Any?>? {
        return controller.getScreenRender(screenName)
    }

    fun createOrGetPresenter(key: String): Presenter{
        if (store.contains(key)){
            return store[key]!!
        }
        checkNotNull (_presenterStore[key]){
            "Presenter don't attach"
        }
        val presenter = _presenterStore[key]!!.createInstance()
        store[key] = presenter
        return presenter
    }

    @Composable
    fun RenderScreen(screenName: String?, params: Any?) {
        controller.RenderScreen(screenName, params)
    }

    fun updateScreenMap(screenMap: HashMap<String, RenderWithParams<Any?>>) {
        controller.updateScreenMap(screenMap)
    }

    fun setNavigationGraph(list: List<AllowedDestination>) {
        controller.setNavigationGraph(list)
    }

    fun setupBackPressedDispatcher(onBackPressedDispatcher: OnBackPressedDispatcher?) {
        controller.setupBackPressedDispatcher(onBackPressedDispatcher)
    }

    fun measureLevel(): Int = controller.measureLevel()

    fun launch(
        screen: String,
        startScreen: String? = null,
        startTabPosition: Int = 0,
        params: Any? = null,
        animationType: AnimationType = AnimationType.None,
        launchFlag: LaunchFlag? = null,
        deepLink: Boolean = false
    ) {
       controller.launch(
           screen,
           startScreen,
           startTabPosition,
           params,
           animationType,
           launchFlag,
           deepLink
       )
    }

    open fun popBackStack() {
        controller.popBackStack()
    }


    fun backToScreen(screenName: String) {
        controller.backToScreen(screenName)
    }

    fun findRootController(): RootController {
        return controller.findRootController()
    }


    fun findModalController(): ModalController {
        return controller.findModalController()
    }

    fun attachModalController(modalController: ModalController) {
        controller.attachModalController(modalController)
    }


    fun drawCurrentScreen(startScreen: String? = null, startParams: Any? = null) {
        controller.drawCurrentScreen(startScreen, startParams)
    }


    fun setDeepLinkUri(path: String?) {
        controller.setDeepLinkUri(path)
    }
}