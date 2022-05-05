package com.io.navigation

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.core.LaunchFlag

fun RootComposeBuilder.buildWithPresenter(
    rootControllerType: RootControllerType = RootControllerType.Root,
    backgroundColor: Color = Color.White,
    presenterFactory: PresenterFactory
): RootWithPresenterController{
    val screens = javaClass.getDeclaredField("_screens")
    screens.isAccessible = true
    val screenMap = javaClass.getDeclaredField("_screenMap")
    screenMap.isAccessible = true

    return RootWithPresenterController(
        presenterFactory = presenterFactory,
        rootControllerType = rootControllerType,
        backgroundColor = backgroundColor,
    ).apply {
        @Suppress("UNCHECKED_CAST")
        updateScreenMap(screenMap.get(this@buildWithPresenter) as HashMap<String, RenderWithParams<Any?>>)
        @Suppress("UNCHECKED_CAST")
        setNavigationGraph(screens.get(this@buildWithPresenter) as MutableList<AllowedDestination>)
    }

}

fun <Scope: Any> RootWithPresenterController.pushAndCreateScope(
    screen: String,
    params: Any? = null,
    createScope: () -> Scope,
    launchFlag: LaunchFlag? = null,
    presenterFactory: (() -> PresenterFactory)? = null
){


    push(
        screen = screen,
        params = params,
        launchFlag = launchFlag,
        presenterFactory = presenterFactory
    )
}

fun RootController.asPresenterController(): RootWithPresenterController{
    return this as RootWithPresenterController
}
