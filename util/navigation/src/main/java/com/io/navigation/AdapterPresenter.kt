package com.io.navigation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext


abstract class AdapterPresenter<Key: Any>(
    internal val config: Config
){
    protected abstract val screenFlow: Flow<Key>
    internal val owner = PresenterStoreOwner<Key>()

    private fun updateScreen(key: Key) = owner.updateScreen(key)
    fun pop() { owner.pop() }


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