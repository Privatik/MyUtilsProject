package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.screen.Screen

val LocalPresenterController = compositionLocalOf<PresenterFactory> {
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
        LocalPresenterController provides factory.invoke()
    ) {
        content()
    }
}