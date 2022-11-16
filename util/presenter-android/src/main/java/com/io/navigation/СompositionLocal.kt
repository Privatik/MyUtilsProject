package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner

internal val LocalPresenterOwnerController = compositionLocalOf<PresenterStoreOwner<out Any>> {
    error("No PresenterStoreOwner provider")
}

