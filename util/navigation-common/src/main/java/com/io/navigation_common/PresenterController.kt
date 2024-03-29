package com.io.navigation_common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


abstract class PresenterController<Key: Any>(
    private val owner: PresenterStoreOwner<Key>
){
    protected abstract val screenFlow: Flow<Key>

    private fun updateScreen(key: Key) = owner.updateCurrentScreen(key)

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