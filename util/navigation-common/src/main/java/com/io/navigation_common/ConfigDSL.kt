package com.io.navigation_common

fun builder(body: Config.() -> Unit): Config {
    return Config().apply(body)
}