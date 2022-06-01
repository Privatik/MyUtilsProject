package com.io.myutilsproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.io.navigation.AdapterPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.NavConfiguration

class OdesseyPresenter(
    controller: RootController
): AdapterPresenter<NavConfiguration, RootController>(controller) {
    override val currentScreen: Flow<NavConfiguration> = controller.currentScreen

    override fun getBackStack(): List<NavConfiguration> {
        return emptyList()
    }



}

class GooglePresenter(
    controller: NavHostController
): AdapterPresenter<NavBackStackEntry, NavHostController>(controller) {
    override val currentScreen: Flow<NavBackStackEntry> = controller.currentBackStackEntryFlow

    override fun getBackStack(): List<NavBackStackEntry> {
        return emptyList()
    }
}