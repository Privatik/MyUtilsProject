package com.io.navigation_common

import java.lang.ref.WeakReference
import java.util.WeakHashMap

open class PresenterStoreOwner<Key: Any>(
    private val keyBackStack: PresenterBackStack<Key> = KeyBackStack(),
    private val keyAdapter: PresenterKeyAdapter<Key>
){

    private val sharedPresenterStore = SharedPresenterStore<Key>()
    private val simpleStores = HashMap<Key, SimplePresenterStore>()

    private val keyTag = HashMap<String, Key>()
    private val tagsStores = HashMap<Key, TagPresenterStore>()

    protected val restorePresenterStoreOwner: RestorePresenterStoreOwner<Key> = DefaultRestorePresenterStoreOwner(
        presenterBackStack = keyBackStack,
        keyTag = keyTag,
        sharedPresenterStore = sharedPresenterStore
    )

    private fun removeUnnecessaryPresenters(){
        keyStore
            .map { wKey -> wKey.get() }
            .forEach { key ->
                removeUnnecessaryPresenters(key)
            }
    }

    private fun removeUnnecessaryPresenters(key: Key){
        simpleStores.remove(key)?.clear()
        sharedPresenterStore.clearByKey(key)
        tagsStores.remove(key)?.apply {
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
        val currentKey = getCurrentKey()

        if (tag != null){
            val tagStore = createOrGetTag(tag, currentKey)
            return tagStore.createOrGet(clazz, factory)
        }

        return if (isShared){
            sharedPresenterStore.createOrGetSharedPresenter<P>(currentKey, clazz, factory)
        } else {
            val store = createOrGetPresenterStore(currentKey)
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }

    private fun createOrGetTag(tag: String, currentKey: Key): TagPresenterStore{
        tagsStores[keyTag[tag]]?.let {
            return it
        }
        val tagStore = TagPresenterStore(tag)
        tagsStores[currentKey] = tagStore
        keyTag[tag] = currentKey

        return tagStore
    }

    private fun createOrGetPresenterStore(currentKey: Key): SimplePresenterStore {
        simpleStores[currentKey]?.let {
            return it
        }
        val store = SimplePresenterStore()
        simpleStores[currentKey] = store

        return store
    }

    private fun getCurrentKey(): Key{
        return keyAdapter.getKey()
    }

}