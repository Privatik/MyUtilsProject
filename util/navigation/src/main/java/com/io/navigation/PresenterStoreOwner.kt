package com.io.navigation

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.Delegates

internal class PresenterStoreOwner<Key: Any>{
    private val sharedPresenters = ConcurrentHashMap<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = ConcurrentHashMap<Key, HashSet<Class<out UIPresenter>>>()

    private val stores = ConcurrentHashMap<Key, PresenterStore>()
    private val backStack = Stack<Key>()

    private var _currentKey: Key by Delegates.notNull()
    private val currentKey: Key
        get() = _currentKey

    fun updateScreen(key: Key){
        _currentKey = key
        backStack.push(key)
    }

    fun deleteBackStackUntilKey(){
        var screen = backStack.peek()
        while (screen != currentKey){
            backStack.pop()
            stores[screen]!!.clear()

            sharedScreenWithSharedPresenter[screen]?.forEach {
                val sharedPresenter = sharedPresenters[it]!!

                val count = sharedPresenter.count
                if (count > 1){
                    sharedPresenters.remove(it)!!.presenter.clear()
                } else {
                    sharedPresenters[it] = sharedPresenter.copy(count = count - 1)
                }
            }

            screen = backStack.peek()
        }
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
        stores[currentKey]?.let {
            return it
        }
        val store = PresenterStore()
        stores[currentKey] = store

        return store
    }
}