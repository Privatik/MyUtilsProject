package com.io.navigation

import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation

fun RootWithPresenterController.launch(
    screen: String,
    startScreen: String? = null,
    startTabPosition: Int = 0,
    params: Any? = null,
    animationType: AnimationType = AnimationType.None,
    launchFlag: LaunchFlag? = null,
    presenterFactory: (() -> PresenterFactory)
) {

    updateFactory(presenterFactory)

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

fun RootWithPresenterController.present(
    screen: String,
    startTabPosition: Int = 0,
    startScreen: String? = null,
    params: Any? = null,
    launchFlag: LaunchFlag? = null,
    presenterFactory: (() -> PresenterFactory)
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

fun RootWithPresenterController.push(
    screen: String,
    params: Any? = null,
    launchFlag: LaunchFlag? = null,
    presenterFactory: (() -> PresenterFactory)
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