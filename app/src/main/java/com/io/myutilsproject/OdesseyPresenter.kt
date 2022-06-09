package com.io.myutilsproject

import androidx.navigation.NavHostController
import com.io.navigation.AdapterPresenter
import kotlinx.coroutines.flow.*
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.helpers.FlowBundle
import ru.alexgladkov.odyssey.compose.helpers.MultiStackBundle

class OdesseyPresenter(
    private var controller: RootController
): AdapterPresenter<String>() {

//    private fun backStack(): List<ScreenInteractor>{
//        val backStack = RootController::class.java.getDeclaredField("_backstack")
//        backStack.isAccessible = true
//
//        @Suppress("UNCHECKED_CAST")
//        return backStack.get(controller) as List<Screen>
//    }

    private var parentController: RootController? = null
    private var isRestartedParentController = false


    override val screenFlow: Flow<String>
        get() = controller
        .currentScreen
        .map { it.screen.key }

    val updateControllerFlow: Flow<Unit>
        get() = controller
            .currentScreen
            .filter {
                val params = it.screen.params

                when (params){
                    is FlowBundle -> {
                        updateController(params.rootController)
                        true
                    }
                    is MultiStackBundle -> {
                        updateController(params.rootController)
                        true
                    }
                    else -> false
                }
            }
            .map { }

    val updateParentControllerFlow: Flow<Unit>
        get() =
            (parentController?.currentScreen ?: flow {  })
                .filter {
                    if (isRestartedParentController){
                        isRestartedParentController = false
                        false
                    } else true
                }
                .onEach{
                    parentController?.let { it1 -> updateController(it1) }
                }
                .map { }

    private fun updateController(controller: RootController){
        this.controller = controller
        parentController = controller.parentRootController
        isRestartedParentController = true
    }
}

class GooglePresenter(
    controller: NavHostController,
): AdapterPresenter<String>() {

    override val screenFlow: Flow<String> = controller.currentBackStackEntryFlow.map { it.id }
}