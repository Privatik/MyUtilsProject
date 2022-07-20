package com.io.navigation

import androidx.lifecycle.ViewModel
import com.io.navigation_common.PresenterStoreOwner

internal class ViewModelForPresenter<Key: Any>(): ViewModel() {
    val owner = PresenterStoreOwner<Key>()
}