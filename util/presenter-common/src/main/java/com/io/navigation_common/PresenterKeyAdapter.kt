package com.io.navigation_common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


abstract class PresenterKeyAdapter<Key: Any>(
    private val owner: PresenterStoreOwner<Key>
){
    protected abstract val screenFlow: Flow<Key>

    private fun push(key: Key) =
    private fun pop(key: Key? = null) = owner.pop(key)
    protected abstract fun getKey(): Key

    protected open suspend fun afterUpdateScreen(key: Key) {}
    protected open suspend fun beforeUpdateScreen(key: Key) {}

    fun updateScreen(): Flow<Key>{
        return screenFlow
            .onEach {
                beforeUpdateScreen(it)
                updateScreen(it)
                afterUpdateScreen(it)
            }
    }


}