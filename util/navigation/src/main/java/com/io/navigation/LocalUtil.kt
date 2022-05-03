package com.io.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalRootFacade = compositionLocalOf<RootFacade> {
    error("No root controller provider")
}

val LocalPresenterFactory = compositionLocalOf<PresenterFactory> {
    error("No factory provider")
}
