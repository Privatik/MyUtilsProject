package com.io.navigation_common

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

open class PresenterStoreOwner<Key: Any>(
    private val keyBackStack: PresenterBackStack<Key> = KeyBackStack(),
){
    private val sharedPresenterStore = SharedPresenterStore<Key>()
    private val simpleStores = HashMap<Key, SimplePresenterStore>()

    private val keyTag = HashMap<String, Key>()
    private val tagsStores = HashMap<Key, TagPresenterStore>()

    private val backStack: Stack<Key>
        get() = keyBackStack.backStack

    private val currentKey: Key
        get() = backStack.peek()

    protected val restorePresenterStoreOwner: RestorePresenterStoreOwner<Key> = DefaultRestorePresenterStoreOwner(
        presenterBackStack = keyBackStack,
        keyTag = keyTag,
        sharedPresenterStore = sharedPresenterStore
    )

    internal fun updateCurrentScreen(key: Key){
        keyBackStack.navigateOrPop(key){ deleteKey ->
            removeUnnecessaryPresenters(deleteKey)
        }
    }

    private fun removeUnnecessaryPresenters(deleteKey: Key){
        simpleStores.remove(deleteKey)?.clear()
        sharedPresenterStore.clearByKey(deleteKey)
        tagsStores.remove(deleteKey)?.apply {
            keyTag.remove(tag)
            clear()
        }
    }

    fun <P: UIPresenter> createPresenter(
        tag: String? = null,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        if (tag != null){
            val tagStore = createOrGetTag(tag)
            return tagStore.createOrGet(clazz, factory)
        }

        return if (isShared){
            sharedPresenterStore.createOrGetSharedPresenter<P>(currentKey, clazz, factory)
        } else {
            val store = createOrGetPresenterStore()
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }

    private fun createOrGetTag(tag: String): TagPresenterStore{
        tagsStores[keyTag[tag]]?.let {
            return it
        }
        val tagStore = TagPresenterStore(tag)
        tagsStores[currentKey] = tagStore
        keyTag[tag] = currentKey

        return tagStore
    }

    private fun createOrGetPresenterStore(): SimplePresenterStore {
        simpleStores[currentKey]?.let {
            return it
        }
        val store = SimplePresenterStore()
        simpleStores[currentKey] = store

        return store
    }

}