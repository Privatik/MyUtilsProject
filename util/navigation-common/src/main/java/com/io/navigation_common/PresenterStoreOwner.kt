package com.io.navigation_common

import java.util.*
import kotlin.properties.Delegates

open class PresenterStoreOwner<Key: Any>(){
    private val sharedPresenterStore = SharedPresenterStore<Key>()
    private val stores = HashMap<Key, SimplePresenterStore>()

    protected val backStack: Stack<Key> = Stack()

    private var _currentKey: Key by Delegates.notNull()
    private val currentKey: Key
        get() = _currentKey

    @Volatile
    private var isPop = false

    internal fun updateScreen(key: Key){
        _currentKey = key
        if (isPop){
            isPop = false
            deleteBackStackUntilKey(key)
        } else {
            backStack.push(key)
        }
    }

    internal fun pop(){ isPop = true }

    private fun deleteBackStackUntilKey(key: Key){
        var screen = backStack.peek()
        while (screen != key){
            backTo(screen)
            screen = backStack.peek()
        }
    }

    private fun backTo(screen: Key){
        backStack.pop()
        stores[screen]?.clear()
        sharedPresenterStore.clearByKey(screen)
    }

    fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        return if (isShared){
            sharedPresenterStore.createOrGetSharedPresenter<P>(currentKey, clazz, factory)
        } else {
            val store = createOrGetPresenterStore()
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }

    private fun createOrGetPresenterStore(): SimplePresenterStore {
        stores[currentKey]?.let {
            return it
        }
        val store = SimplePresenterStore()
        stores[currentKey] = store

        return store
    }
}