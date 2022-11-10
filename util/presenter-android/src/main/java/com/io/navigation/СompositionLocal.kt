package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner

internal val LocalPresenterKeyAdapter = compositionLocalOf<PresenterKeyAdapter<out Any>> {
    error("No PresenterController provider")
}

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<out Any>> {
    error("No PresenterStoreOwner provider")
}

@Composable
fun <A : PresenterKeyAdapter<out Any>> presenterController(): A {
    @Suppress("UNCHECKED_CAST")
    return LocalPresenterKeyAdapter.current as A
}

