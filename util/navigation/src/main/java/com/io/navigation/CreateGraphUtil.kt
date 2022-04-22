package com.io.navigation

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher

fun <Presenter: UIPresenter> ComponentActivity.createGraph(
    startScreen: String,
//    factoryForPresenter: PresenterFactory<Presenter>,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
){
    val rootController = RootControllerWithPresenterBuilder<Presenter>(
        rootComposeBuilder = RootComposeBuilder().apply { navigationGraph() }
    ).build()

    rootController.setDeepLinkUri(intent?.data?.path)
    rootController.addBackNavigator(this)

    setContent {
        CompositionLocalProvider(
            *providers,
            LocalRootFacadeController provides rootController
        ) {
            LaunchedEffect(Unit){
                rootController.currentScreen.collect{

                }
            }

            ModalNavigator {
                Navigator(startScreen)
            }
        }
    }
}

fun <Presenter: Any> RootControllerFacade<Presenter>.addBackNavigator(activity: ComponentActivity){
    val dispatcher = activity.onBackPressedDispatcher
    val rootDispatcher = OnBackPressedDispatcher()
    dispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            rootDispatcher.onBackPressed()
        }
    })

    onApplicationFinish = {
        activity.finish()
    }

    setupBackPressedDispatcher(rootDispatcher)
}