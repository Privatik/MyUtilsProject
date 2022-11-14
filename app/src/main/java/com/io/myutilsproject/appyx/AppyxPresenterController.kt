package com.io.myutilsproject.appyx

import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.io.myutilsproject.SimpleNode
import kotlinx.coroutines.flow.*

class AppyxPresenterKeyAdapter(
    owner: AppyxAndroidPresenterStoreOwner,
    private val backStack: BackStack<SimpleNode>
){
    private val ids = HashSet<NavKey<SimpleNode>>()

//    val backState = backStack
//        .elements
//        .map { navElements ->
//            Pair(
//                navElements.first { element -> element.targetState == BackStack.State.ACTIVE },
//                navElements.firstOrNull { element -> element.targetState == BackStack.State.DESTROYED }
//            )
//        }
//        .filter {
//            var wasChange = false
//            if (it.second != null && ids.contains(it.second!!.key)) {
//                ids.remove(it.second!!.key)
//                wasChange = true
//            }
//            if (!ids.contains(it.first.key)){
//                ids.add(it.first.key)
//                wasChange = true
//            }
//            wasChange
//        }
//        .onEach {
//            owner.updateCurrentScreen(it.first.key)
//            it.second?.let { element -> owner.updateCurrentScreen(element.key) }
//        }

}