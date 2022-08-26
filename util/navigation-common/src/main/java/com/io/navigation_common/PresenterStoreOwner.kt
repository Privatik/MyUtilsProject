package com.io.navigation_common

import java.util.*
import kotlin.properties.Delegates

open class PresenterStoreOwner<Key: Any>(
    private val keyBackStack: PresenterBackStack<Key> = KeyBackStack()
){
    private val sharedPresenterStore = SharedPresenterStore<Key>()
    private val stores = HashMap<Key, SimplePresenterStore>()

    protected val backStack: Stack<Key>
        get() = keyBackStack.backStack

    private val currentKey: Key
        get() = backStack.peek()

    protected fun saveInfoAboutShared(): Map<String, HashSet<Key>>{
        return sharedPresenterStore.save()
    }

    protected fun restoreInfoAboutShared(retainKeys: Map<String, HashSet<Key>>){
        sharedPresenterStore.restore(retainKeys)
    }

    internal fun updateScreen(key: Key){
        keyBackStack.navigateOrPop(key){ deleteKey ->
            back(deleteKey)
        }
    }

    private fun back(deleteKey: Key){
        stores.remove(deleteKey)?.clear()
        sharedPresenterStore.clearByKey(deleteKey)
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