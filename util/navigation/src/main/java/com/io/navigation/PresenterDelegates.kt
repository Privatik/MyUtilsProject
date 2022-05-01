package com.io.navigation

import androidx.compose.runtime.Composable

@Composable
public inline fun <reified P: UIPresenter> presenter(): P {
    return presenter(P::class.java)
}

@Composable
public fun <P : UIPresenter> presenter(
    clazz: Class<out UIPresenter>
): P {
    checkNotNull(LocalPresenterFactory.current)
    val factory = LocalPresenterFactory.current

    return factory.create(clazz) as P
}
