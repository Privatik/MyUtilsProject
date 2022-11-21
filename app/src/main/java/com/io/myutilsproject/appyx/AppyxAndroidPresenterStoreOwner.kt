package com.io.myutilsproject.appyx

import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.active
import com.io.myutilsproject.SimpleNode
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterStoreOwner

class AppyxPresenterAdapter(
    private val backStack: BackStack<SimpleNode>
): PresenterStoreOwner.Adapter<NavKey<SimpleNode>>(){

    override fun getGuide(): NavKey<SimpleNode> {
        val el = backStack.active!!
        println("Navigation $el its id = ${el.key.id}")
        return el.key
    }

    override fun getCacheKey(currentGuide: NavKey<SimpleNode>): String {
        return currentGuide.id
    }

    override fun isOptionallyVerifyValidGuide(guide: NavKey<SimpleNode>?): Boolean {
        if (guide == null) return false
        val element = backStack.elements.value.lastOrNull { it.key == guide } ?: return false

        return element.targetState != BackStack.State.DESTROYED
    }

}

class AppyxPresenterStoreOwner(
   adapter: AppyxPresenterAdapter
): AndroidPresenterStoreOwner<NavKey<SimpleNode>>(adapter)