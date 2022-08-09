package com.io.navigation_common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.util.*


abstract class PresenterController<Key: Any>(
    private val owner: PresenterStoreOwner<Key>
){
    protected abstract val screenFlow: Flow<Key>

    private fun updateScreen(key: Key) = owner.updateScreen(key)
    fun clearDontUsePresenter() { owner.pop() }

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