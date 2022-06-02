package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalPresenterController = compositionLocalOf<AdapterPresenter<*, *>> {
    error("No root controller provider")
}

fun emptyPresenter(): PresenterFactory{
    return EmptyPresenterFactory()
}

@Composable
fun UpdatePresenter(
    factory: () -> PresenterFactory,
){
    val adapter = LocalPresenterController.current
    adapter.updateFactory(factory)
}