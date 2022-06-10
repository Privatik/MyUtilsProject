package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

fun emptyPresenter(): PresenterFactory{
    return EmptyPresenterFactory()
}

@Composable
fun RebuildConfig(
    rule: Config.() -> Unit
){
    val adapter = LocalAdapterController.current
    adapter.config.apply(rule)
}