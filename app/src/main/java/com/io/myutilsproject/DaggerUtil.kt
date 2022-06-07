package com.io.myutilsproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.navigation.PresenterFactory

private val appFactory = DaggerAppComponent.builder().build().factory
fun createAppComponent(): PresenterFactory{
    return appFactory
}

private val nextFactory = DaggerNextComponent.builder().build().factory
fun createNextComponent(): PresenterFactory{
    return nextFactory
}