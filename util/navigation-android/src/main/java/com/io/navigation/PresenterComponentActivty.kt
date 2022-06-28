package com.io.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.navigation_common.Config
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner

abstract class PresenterComponentActivity<Key: Any>: ComponentActivity(){

    @Suppress("UNCHECKED_CAST")
    private val vm: ViewModelForPresenter<Key> by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewModelForPresenter<Key>(initializeConfig()) as T
            }
        }
    }

    abstract fun initializeConfig(): Config

}