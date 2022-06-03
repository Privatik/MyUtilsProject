package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<*>> {
    error("No root controller provider")
}

internal val LocalPresenterFactoryController = compositionLocalOf<PresenterFactory> {
    error("No root controller provider")
}

fun emptyPresenter(): PresenterFactory{
    return EmptyPresenterFactory()
}

@Composable
fun UpdatePresenter(
    factory: () -> PresenterFactory,
    content: @Composable () -> Unit
){
    CompositionLocalProvider(
        LocalPresenterFactoryController provides factory.invoke()
    ) {
        content()
    }
}

@Composable
fun UpdatePresenter(
    factory: PresenterFactory,
    content: @Composable () -> Unit
){
    CompositionLocalProvider(
        LocalPresenterFactoryController provides factory
    ) {
        content()
    }
}