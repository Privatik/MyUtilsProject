package com.io.navigation

import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TopNavConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import kotlin.reflect.KClass

fun <Presenter: Any> RootControllerFacade<Presenter>.push(screen: String, params: Any? = null, launchFlag: LaunchFlag? = null) {
    launch(screen = screen, startScreen = null, startTabPosition = 0, params = params, animationType = defaultPushAnimation(), launchFlag)
}

fun <Presenter: Any> RootControllerFacade<Presenter>.push(screen: String, params: Any? = null) {
    launch(screen = screen, params = params, animationType = defaultPushAnimation())
}

fun <Presenter: Any> RootControllerWithPresenterBuilder<Presenter>.screen(
    name: String,
    presenter: KClass<out Presenter>,
    content: RenderWithParams<Any?>,
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content),
        presenter = presenter
    )
}

fun <Presenter: Any> RootControllerWithPresenterBuilder<Presenter>.flow(
    name: String,
    builder: FlowBuilder.() -> Unit
) {
    addFlow(
        key = name,
        flowBuilderModel = FlowBuilder(name).apply(builder).build()
    )
}


fun <Presenter: Any> RootControllerWithPresenterBuilder<Presenter>.bottomNavigation(
    name: String,
    tabsNavModel: TabsNavModel<BottomNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}


fun <Presenter: Any> RootControllerWithPresenterBuilder<Presenter>.topNavigation(
    name: String,
    tabsNavModel: TabsNavModel<TopNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}