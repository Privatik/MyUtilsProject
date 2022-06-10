package com.io.navigation

fun builder(body: Config.() -> Unit): Config{
    return Config().apply(body)
}