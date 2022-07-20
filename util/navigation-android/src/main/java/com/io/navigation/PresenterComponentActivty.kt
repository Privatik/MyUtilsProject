package com.io.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.navigation_common.PresenterStoreOwner

abstract class PresenterComponentActivity<Key: Any>: ComponentActivity(){

    @Suppress("UNCHECKED_CAST")
    private val vm: ViewModelForPresenter<Key> by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewModelForPresenter<Key>() as T
            }
        }
    }

    val presenterStoreOwner: PresenterStoreOwner<Key> by lazy(LazyThreadSafetyMode.NONE) { vm.owner }


}