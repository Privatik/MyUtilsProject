package com.io.myutilsproject

import com.io.navigation_common.PresenterFactory

fun createAppComponent(): PresenterFactory {
    return DaggerAppComponent.builder().build().factory
}

fun createNextComponent(): PresenterFactory {
    return DaggerNextComponent.builder().build().factory
}