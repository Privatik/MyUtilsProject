package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf

class SaveHandle(
    private val bundle: Bundle
) {
    private val keys = hashSetOf<String>()
    private val flowMap = hashMapOf<String, MutableSharedFlow<Any>>()

    fun <T: Any> handleAsFlow(key: String): Flow<T> {
        @Suppress("UNCHECKED_CAST")
        return flowMap.getOrElse(key){
            val flow = MutableSharedFlow<Any>()
            flowMap[key] = flow
            flow
        } as Flow<T>
    }

    suspend fun <T: Any> put(key: String, body: T){
        putInBundle(key, body)
        flowMap[key]?.emit(body)

    }

    fun <T: Any> tryPut(key: String, body: T){
        putInBundle(key, body)
        flowMap[key]?.tryEmit(body)
    }

    @Synchronized
    private fun <T: Any> putInBundle(key: String, body: T) {
        keys.add(key)
        bundle.putAll(bundleOf(key to body))
    }

    fun <T: Any> get(key: String): T?{
        @Suppress("UNCHECKED_CAST")
        return bundle.get(key) as? T
    }

    fun update(bundle: Bundle){
        this.bundle.putAll(bundle)
    }

    fun getBundle(): Bundle{
        return bundle
    }
}