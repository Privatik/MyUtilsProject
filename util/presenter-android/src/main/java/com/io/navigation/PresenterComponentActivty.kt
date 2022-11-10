package com.io.navigation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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