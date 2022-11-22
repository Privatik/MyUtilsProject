package com.io.myutilsproject

import androidx.lifecycle.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.launch

class GooglePresenterAdapter(
    private val controller: NavHostController
): PresenterStoreOwner.Adapter<NavBackStackEntry>() {

    override fun getGuide(): NavBackStackEntry = controller.currentBackStackEntry!!

    override fun getCacheKey(currentGuide: NavBackStackEntry): String = currentGuide.id

    override fun isOptionallyVerifyValidGuide(guide: NavBackStackEntry?): Boolean {
        if (guide == null) return false
        controller.backQueue.lastOrNull { it.id == guide.id } ?: return false

        return guide.lifecycle.currentState != Lifecycle.State.DESTROYED
    }
}

class GoogleOwnerPresenter(
    keyAdapter: GooglePresenterAdapter
): AndroidPresenterStoreOwner<NavBackStackEntry>(keyAdapter)