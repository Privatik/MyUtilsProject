package com.io.navigation

import androidx.lifecycle.ViewModel

internal class ViewModelForPresenter(): ViewModel() {
    val owner = AndroidPresenterStoreOwner()
}