package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

internal val LocalAdapterController = compositionLocalOf<AdapterPresenter<out Any>> {
    error("No adapter provider")
}


@Composable
fun <A : AdapterPresenter<out Any>> adapter(): A {
    @Suppress("UNCHECKED_CAST")
    return LocalAdapterController.current as A
}

