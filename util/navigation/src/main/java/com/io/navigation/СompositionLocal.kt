package com.io.navigation

import androidx.compose.runtime.compositionLocalOf

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<*>> {
    error("No root controller provider")
}

internal val LocalPresenterFactoryController = compositionLocalOf<PresenterFactory> {
    EmptyPresenterFactory()
}
