package com.io.myutilsproject

fun main(){
    test()
}

fun test(){
    val appComponent = DaggerAppComponent.builder().build()

    println("AppComponent ${appComponent.factory.map.map { it.key.simpleName }}")

    val nextComponent = DaggerNextComponent.builder().deps(appComponent).build()

    println("NextComponent ${nextComponent.factory.map.map { it.key.simpleName }}")
}