package com.io.myutilsproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.navigation.PresenterFactory

fun createAppComponent(): PresenterFactory{
    return DaggerAppComponent.builder().build().factory
}

fun createNextComponent(): PresenterFactory{
    return DaggerNextComponent.builder().deps(PresenterStore.presenterDeps).build().factory
}