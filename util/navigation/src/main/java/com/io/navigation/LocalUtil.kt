package com.io.navigation

import androidx.compose.runtime.compositionLocalOf
import ru.alexgladkov.odyssey.compose.RootController

val LocalRootFacadeController = compositionLocalOf<RootControllerFacade<out Any>> {
    error("No root controller provider")
}