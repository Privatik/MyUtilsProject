package com.io.navigation

import java.util.concurrent.ConcurrentHashMap

internal fun <T, K> HashMap<T, K>.initializeOrGet(key: T, initializeValue: K):K{
    return get(key) ?: let {
        put(key, initializeValue)
        initializeValue
    }
}

internal fun <T: Any, K: Any> ConcurrentHashMap<T, K>.initializeOrGet(key: T, initializeValue: K):K{
    return get(key) ?: let {
        put(key, initializeValue)
        initializeValue
    }
}

internal fun writeMessage(message: String){
    println("Presenter $message")
}