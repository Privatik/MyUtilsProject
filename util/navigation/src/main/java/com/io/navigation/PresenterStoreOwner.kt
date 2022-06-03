package com.io.navigation

import java.util.*
import kotlin.properties.Delegates

class PresenterStoreOwner<Key: Any> internal constructor(){
    private val sharedPresenters = hashMapOf<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = hashMapOf<Key, HashSet<Class<out UIPresenter>>>()

    private val stores = hashMapOf<Key, PresenterStore>()
    private val backStack = Stack<Key>()

    private var _currentKey: Key by Delegates.notNull()
    private val currentKey: Key
        get() = _currentKey

    internal fun updateScreen(key: Key){
        if (key == currentKey) return
        val screenSet = stores.keys
        if (screenSet.contains(key)){
            deleteBackStackUntilKey(key)
        } else {
            addBackStack(key)
        }
    }

    private fun addBackStack(key: Key){
        val store = PresenterStore()
        stores[key] = store
        backStack.push(key)
        _currentKey = key
    }

    private fun deleteBackStackUntilKey(key: Key){
        var screen = backStack.peek()
        while (screen != key){
            backStack.pop()
            stores[screen]!!.clear()
            screen = backStack.peek()
        }
        _currentKey = screen
    }

    fun <P: UIPresenter> createOrGetSharedPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (sharedPresenters.containsKey(clazz)){
            val sharedPresenter = sharedPresenters[clazz]!!
            val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
                currentKey,
                hashSetOf()
            )
            if (!screenWithSharedPresenter.contains(clazz)){
                sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
            }

            @Suppress("UNCHECKED_CAST")
            return sharedPresenter.presenter as P
        } else {
            val presenter = factory.create<P>(clazz)
            sharedPresenters[clazz] = SharedPresenterBody(presenter = presenter)
            val clazzSet = hashSetOf(clazz)
            sharedScreenWithSharedPresenter[currentKey] = clazzSet
            presenter.build()
            return presenter
        }
    }

    fun createOrGetPresenterStore(): PresenterStore {
        return stores[currentKey]!!
    }
}