package com.io.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


abstract class AdapterPresenter<Key: Any,Controller> constructor(
    protected val controller: Controller,
) {

    private val owner = PresenterStoreOwner<Key>()

    protected fun updateScreen(key: Key) = owner.updateScreen(key)
    fun pop() = owner.deleteBackStackUntilKey()

    abstract suspend fun updateScreen()


}