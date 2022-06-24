package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner

internal val LocalPresenterController = compositionLocalOf<PresenterController<out Any>> {
    error("No PresenterController provider")
}

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<out Any>> {
    error("No PresenterStoreOwner provider")
}


@Composable
fun <A : PresenterController<out Any>> presenterController(): A {
    @Suppress("UNCHECKED_CAST")
    return LocalPresenterController.current as A
}

