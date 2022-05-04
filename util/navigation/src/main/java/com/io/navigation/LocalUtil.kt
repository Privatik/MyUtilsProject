package com.io.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavigationFactory = compositionLocalOf<RootWithPresenterController> {
    error("No factory provider")
}
