package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<*>> {
    error("No owner provider")
}

internal val LocalPresenterFactoryController = compositionLocalOf<PresenterFactory> {
    EmptyPresenterFactory()
}

internal val LocalAdapterController = compositionLocalOf<AdapterPresenter<out Any>> {
    error("No adapter provider")
}

internal val LocalConfigController = compositionLocalOf<Config> {
    error("No config provider")
}

@Composable
fun <A : AdapterPresenter<out Any>> adapter(): A {
    @Suppress("UNCHECKED_CAST")
    return LocalAdapterController.current as A
}

