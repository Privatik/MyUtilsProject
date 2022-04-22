package com.io.navigation

import androidx.compose.runtime.Composable

@Composable
public inline fun <reified P: UIPresenter> presenter(): P {
    return presentero(P::class.java)
}

@Composable
public fun <P: UIPresenter> presentero(
    clazz: Class<P>
): P{
    checkNotNull(LocalPresenterFactory.current)
    val factory = LocalPresenterFactory.current

    return factory.create(clazz)
}
