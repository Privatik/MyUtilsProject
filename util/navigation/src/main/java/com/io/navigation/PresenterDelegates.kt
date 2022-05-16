package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
public inline fun <reified P: UIPresenter> presenter(): P {
    return presenter(P::class.java)
}

@Composable
public fun <P : UIPresenter> presenter(
    clazz: Class<out UIPresenter>
): P {
    checkNotNull(LocalRootController.current)
    val controller = LocalRootController.current.asPresenterController()

    return remember {
        controller.createPresenter(clazz)
    }
}
