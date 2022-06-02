package com.io.myutilsproject

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AdapterPresenter
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

class OdesseyPresenter(
    controller: RootController
): AdapterPresenter<ScreenInteractor, RootController>(controller) {

    override fun getBackStack(): List<ScreenInteractor> {
        return backStack()
    }

    private fun backStack(): List<ScreenInteractor>{
        val backStack = RootController::class.java.getDeclaredField("_backstack")
        backStack.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        return backStack.get(controller) as List<Screen>
    }

    override fun getCurrentScreen(): ScreenInteractor {
        return controller.currentScreen.value.screen
    }


}

class GooglePresenter(
    controller: NavHostController,
): AdapterPresenter<NavBackStackEntry, NavHostController>(controller) {

    override fun getBackStack(): List<NavBackStackEntry> {
        return controller.backQueue
    }

    override fun getCurrentScreen(): NavBackStackEntry {
        return controller.currentBackStackEntry!!
    }
}