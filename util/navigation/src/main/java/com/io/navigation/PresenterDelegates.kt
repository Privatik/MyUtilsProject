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
    checkNotNull(LocalNavigationFactory.current)
    val factory = LocalNavigationFactory.current

    return factory.createPresenter(clazz)
}
