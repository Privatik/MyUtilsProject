package com.io.myutilsproject

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterStoreOwner

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

class GooglePresenterAdapter(
    private val controller: NavHostController,
    private val subscribeOnDestroyState:(NavBackStackEntry) -> Unit
): PresenterStoreOwner.Adapter<NavBackStackEntry>() {
    private val subscribedIds = hashSetOf<String>()

    override fun getGuide(): NavBackStackEntry = controller.currentBackStackEntry!!

    override fun getCacheKey(currentGuide: NavBackStackEntry): String = currentGuide.let {
        val id = it.id
//
//        if (!subscribedIds.contains(id)){
//            subscribedIds.add(id)
//            subscribeOnDestroyState(it)
//        }

        id
    }

    override fun isOptionallyVerifyValidGuide(guide: NavBackStackEntry?): Boolean {
        if (guide == null) return true

        return guide.lifecycle.currentState == Lifecycle.State.DESTROYED
    }

}

class GoogleOwnerPresenter(
    keyAdapter: GooglePresenterAdapter
): AndroidPresenterStoreOwner<NavBackStackEntry>(keyAdapter)