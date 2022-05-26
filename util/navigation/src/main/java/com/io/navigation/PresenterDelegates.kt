package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
public inline fun <reified P: UIPresenter> presenter(): P {
    return presenter(P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> sharedPresenter(): P {
    return presenter(P::class.java, true)
}

@Composable
public fun <P : UIPresenter> presenter(
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkNotNull(LocalRootController.current)
    val controller = LocalRootController.current.asPresenterController()

    return remember {
        controller.createPresenter(clazz, isShared)
    }
}
