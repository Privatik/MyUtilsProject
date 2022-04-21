package com.io.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalRootFacadeController = compositionLocalOf<RootControllerFacade<out Any>> {
    error("No root controller provider")
}
