package com.io.navigation

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation

class RootFacade(
    private val controller: RootController,
) {

    fun push(
        screen: String,
        params: Any? = null,
        launchFlag: LaunchFlag? = null
    ) {

        controller.launch(
            screen = screen,
            startScreen = null,
            startTabPosition = 0,
            params = params,
            animationType = defaultPushAnimation(),
            launchFlag
        )
    }
}