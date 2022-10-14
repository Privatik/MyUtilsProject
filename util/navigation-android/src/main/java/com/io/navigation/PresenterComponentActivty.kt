package com.io.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner

abstract class PresenterComponentActivity: ComponentActivity(){

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