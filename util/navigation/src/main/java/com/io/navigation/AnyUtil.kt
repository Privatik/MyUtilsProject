package com.io.navigation

internal fun <T, K> HashMap<T, K>.initializeOrGet(key: T, initializeValue: K):K{
    return get(key) ?: let {
        put(key, initializeValue)
        initializeValue
    }
}