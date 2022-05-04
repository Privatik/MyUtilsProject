package com.io.myutilsproject

import com.io.navigation.PresenterFactory

fun createAppComponent(): PresenterFactory{
    return DaggerAppComponent.builder().build().factory
}

//fun createNextComponent(): PresenterFactory{
//    return Dagger
//}