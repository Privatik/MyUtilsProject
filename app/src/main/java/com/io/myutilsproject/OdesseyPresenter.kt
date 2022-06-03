package com.io.myutilsproject

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AdapterPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.screen.Screen
import ru.alexgladkov.odyssey.core.screen.ScreenInteractor

class OdesseyPresenter(
    controller: RootController
): AdapterPresenter<String, RootController>(controller) {

//    private fun backStack(): List<ScreenInteractor>{
//        val backStack = RootController::class.java.getDeclaredField("_backstack")
//        backStack.isAccessible = true
//
//        @Suppress("UNCHECKED_CAST")
//        return backStack.get(controller) as List<Screen>
//    }
//

    override fun updateCurrentScreen(scope: CoroutineScope) {
        controller.currentScreen
            .onEach {
                updateScreen(it.screen.key)
            }
            .launchIn(scope)
    }


}

class GooglePresenter(
    controller: NavHostController,
): AdapterPresenter<String, NavHostController>(controller) {

    override fun updateCurrentScreen(scope: CoroutineScope) {
        controller.currentBackStackEntryFlow
            .onEach {
                updateScreen(it.id)
            }
            .launchIn(scope)
    }
}