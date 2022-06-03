package com.io.myutilsproject

fun createNextScope(): NextComponent{
    val component = DaggerNextComponent
        .builder()
        .build()

    return component
}