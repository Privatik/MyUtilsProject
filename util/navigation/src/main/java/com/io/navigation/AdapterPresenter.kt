package com.io.navigation

import kotlinx.coroutines.CoroutineScope


abstract class AdapterPresenter<Key: Any,Controller>(
    protected val controller: Controller,
) {
    private val owner = PresenterStoreOwner<Key>()

    abstract fun updateCurrentScreen(scope: CoroutineScope)

    fun updateScreen(key: Key) = owner.updateScreen(key)

}