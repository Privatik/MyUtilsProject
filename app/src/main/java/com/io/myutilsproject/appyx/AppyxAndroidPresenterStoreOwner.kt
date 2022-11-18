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
    override fun getGuide(): NavKey<SimpleNode> = backStack.active!!.key

    override fun getCacheKey(currentGuide: NavKey<SimpleNode>): String {
        return currentGuide.id
    }

}

class AppyxStoreOwner(
   adapter: AppyxPresenterAdapter
): AndroidPresenterStoreOwner<NavKey<SimpleNode>>(adapter)