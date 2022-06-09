package com.io.navigation

fun builder(body: Config.() -> Unit): Config{
    return Config(hashMapOf(null to ConfigItem(::emptyPresenter))).apply(body)
}

fun Config.newBuilder(body: Config.() -> Unit): Config{
    return Config(this.items).apply(body)
}