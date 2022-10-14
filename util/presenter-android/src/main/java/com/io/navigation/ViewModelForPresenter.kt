package com.io.navigation

import androidx.lifecycle.ViewModel
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner
import kotlin.properties.Delegates

internal class ViewModelForPresenter(): ViewModel() {
    val owner = AndroidPresenterStoreOwner()
}