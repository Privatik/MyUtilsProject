package com.io.myutilsproject

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AdapterPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
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
    override val screenFlow: Flow<String> = controller.currentScreen.map { it.screen.key }

}

class GooglePresenter(
    controller: NavHostController,
): AdapterPresenter<String, NavHostController>(controller) {

    override val screenFlow: Flow<String> = controller.currentBackStackEntryFlow.map { it.id }
}