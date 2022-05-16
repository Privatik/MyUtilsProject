package com.io.myutilsproject

import com.io.navigation.PresenterFactory
import com.io.navigation.RootWithPresenterController

fun createNextScope(): NextComponent{
    val component = DaggerNextComponent
        .builder()
        .build()

    return component
}