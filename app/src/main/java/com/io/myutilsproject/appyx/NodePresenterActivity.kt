package com.io.myutilsproject.appyx

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.io.navigation.AndroidPresenterStoreOwner

abstract class NodePresenterActivity: NodeActivity() {

    @Suppress("UNCHECKED_CAST")
    private val vm: ViewModelForPresenter by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewModelForPresenter() as T
            }
        }
    }

    val presenterStoreOwner: AndroidPresenterStoreOwner by lazy(LazyThreadSafetyMode.NONE) { vm.owner }
}

private class ViewModelForPresenter: ViewModel(){
    val owner = AndroidPresenterStoreOwner()
}