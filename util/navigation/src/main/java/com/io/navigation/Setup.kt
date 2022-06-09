package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import kotlinx.coroutines.flow.launchIn

@Composable
fun <Key: Any> PresenterCompositionLocalProvider(
    vararg providers: ProvidedValue<*>,
    adapter: AdapterPresenter<Key>,
    canUpdate: Boolean = true,
    content: @Composable () -> Unit
) {

    if (canUpdate) {
        LaunchedEffect(Unit){
            adapter
                .updateScreen()
                .launchIn(this)
        }
    }

    CompositionLocalProvider(
        LocalPresenterOwnerController provides adapter.owner,
        LocalAdapterController provides adapter,
        *providers
    ) {
        content()
    }
}