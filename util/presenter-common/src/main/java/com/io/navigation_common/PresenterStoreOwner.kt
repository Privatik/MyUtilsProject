package com.io.navigation_common

import java.util.UUID
import java.util.WeakHashMap

abstract class PresenterStoreOwner<Guide: Any, CacheKey: Any>(
    private val keyAdapter: PresenterKeyAdapter<Guide>
){
    private val cacheKeyStore = hashSetOf<CacheKey>()
    private val keyStores = WeakHashMap<Guide, CacheKey>()

    private val sharedPresenterStore = SharedPresenterStore<CacheKey>()
    private val tagPresenterStore = TagPresenterStore<CacheKey>()
    private val simpleStores = HashMap<CacheKey, SimplePresenterStore>()

    protected val restorePresenterStoreOwner: RestorePresenterStoreOwner<CacheKey> = DefaultRestorePresenterStoreOwner(
        tagPresenterStore = tagPresenterStore,
        sharedPresenterStore = sharedPresenterStore,
    )

    private fun removeUnnecessaryPresenters(){
        val setAliveCacheKeys = keyStores.values.toSet()
        cacheKeyStore
            .filter { cacheKey -> !setAliveCacheKeys.contains(cacheKey) }
            .onEach (::removeUnnecessaryPresentersByKey)
            .forEach(cacheKeyStore::remove)
    }

    private fun removeUnnecessaryPresentersByKey(cacheKey: CacheKey){
        simpleStores.remove(cacheKey)?.clear()
        sharedPresenterStore.clearByCacheKey(cacheKey)
        tagPresenterStore.clearByCacheKey(cacheKey)
    }

    @Synchronized
    fun <P: UIPresenter> createPresenter(
        tag: String? = null,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        removeUnnecessaryPresenters()
        val currentGuide = getCurrentGuide()
        val keyForCache = keyStores.getOrDefault(currentGuide, getNewKeyForCache())

        if (tag != null){
            return tagPresenterStore.createOrGet(tag, keyForCache, clazz, factory)
        }

        return if (isShared){
            sharedPresenterStore.createOrGetSharedPresenter<P>(keyForCache, clazz, factory)
        } else {
            val store = createOrGetPresenterStore(keyForCache)
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }


    private fun createOrGetPresenterStore(cacheKey: CacheKey): SimplePresenterStore {
        simpleStores[cacheKey]?.let {
            return it
        }
        val store = SimplePresenterStore()
        simpleStores[cacheKey] = store

        return store
    }

    private fun getCurrentGuide(): Guide{
        return keyAdapter.getKey()
    }

    abstract fun getNewKeyForCache(): CacheKey

}