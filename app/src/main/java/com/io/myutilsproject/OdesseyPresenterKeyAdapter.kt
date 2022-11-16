package com.io.myutilsproject

import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterKeyAdapter
import java.util.*

//class OdesseyPresenterKeyAdapter(
//    private var controller: RootController,
//    owner: AndroidPresenterStoreOwner
//): AndroidPresenterKeyAdapter(owner) {
//
////    private fun backStack(): List<ScreenInteractor>{
////        val backStack = RootController::class.java.getDeclaredField("_backstack")
////        backStack.isAccessible = true
////
////        @Suppress("UNCHECKED_CAST")
////        return backStack.get(controller) as List<Screen>
////    }
//
//    private var parentController: RootController? = null
//    private var isRestartedParentController = false
//
//    override val screenFlow: Flow<String>
//        get() = controller
//        .currentScreen
//        .map { it.screen.key }
//
//    val updateControllerFlow: Flow<Unit>
//        get() = controller
//            .currentScreen
//            .filter {
//                val params = it.screen.params
//
//                when (params){
//                    is FlowBundle -> {
//                        updateController(params.rootController)
//                        true
//                    }
//                    is MultiStackBundle -> {
//                        updateController(params.rootController)
//                        true
//                    }
//                    else -> false
//                }
//            }
//            .map { }
//
//    val updateParentControllerFlow: Flow<Unit>
//        get() =
//            (parentController?.currentScreen ?: flow {  })
//                .filter {
//                    if (isRestartedParentController){
//                        isRestartedParentController = false
//                        false
//                    } else true
//                }
//                .onEach{
//                    parentController?.let { it1 -> updateController(it1) }
//                }
//                .map { }
//
//    private fun updateController(controller: RootController){
//        this.controller = controller
//        parentController = controller.parentRootController
//        isRestartedParentController = true
//    }
//}

class GooglePresenterKeyAdapter(
    private val controller: NavHostController
): PresenterKeyAdapter<NavBackStackEntry>() {
    override fun getKey(): NavBackStackEntry = controller.currentBackStackEntry!!

    override fun getCacheKey(): String {
        return getKey().id
    }

}

class GooglePresenterOwner(
    keyAdapter: GooglePresenterKeyAdapter
): AndroidPresenterStoreOwner<NavBackStackEntry>(keyAdapter)