package com.io.navigation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext


abstract class AdapterPresenter<Key: Any,Controller> constructor(
    protected val controller: Controller,
) {
    protected abstract val screenFlow: Flow<Key>
    internal val owner = PresenterStoreOwner<Key>()

    private fun updateScreen(key: Key) = owner.updateScreen(key)
    fun pop() = owner.pop()

    fun updateScreen(): Flow<Key>{
        return screenFlow
            .onEach {
                updateScreen(it)
            }
    }


}