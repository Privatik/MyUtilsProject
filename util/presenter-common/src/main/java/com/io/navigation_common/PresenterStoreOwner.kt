package com.io.navigation_common

import java.util.UUID
import java.util.WeakHashMap

open class PresenterStoreOwner<Guide: Any>(
    private val keyAdapter: PresenterKeyAdapter<Guide>
){
    private val cacheKeyStore = hashSetOf<String>()
    private val keyStores = WeakHashMap<Guide, String>()

    private val sharedPresenterStore = SharedPresenterStore<String>()
    private val simpleStores = HashMap<String, SimplePresenterStore>()

    protected val restorePresenterStoreOwner: RestorePresenterStoreOwner<String> = DefaultRestorePresenterStoreOwner(
        sharedPresenterStore = sharedPresenterStore,
    )

    private fun removeUnnecessaryPresenters(){
        val setAliveCacheKeys = keyStores.values.toSet()
        cacheKeyStore
            .filter { cacheKey -> !setAliveCacheKeys.contains(cacheKey) }
            .onEach (::removeUnnecessaryPresentersByKey)
            .forEach(cacheKeyStore::remove)
    }

    private fun removeUnnecessaryPresentersByKey(cacheKey: String){
        simpleStores.remove(cacheKey)?.clear()
        sharedPresenterStore.clearByCacheKey(cacheKey)
    }

    @Synchronized
    fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        removeUnnecessaryPresenters()
        val keyForCache = addAndGetCacheKey()

        return if (isShared){
            sharedPresenterStore.createOrGetSharedPresenter<P>(keyForCache, clazz, factory)
        } else {
            val store = createOrGetPresenterStore(keyForCache)
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }


    private fun createOrGetPresenterStore(cacheKey: String): SimplePresenterStore {
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

    private fun addAndGetCacheKey(): String{
        val currentGuide = getCurrentGuide()
        val keyForCache = keyStores.getOrElse(currentGuide){
            val newCacheKey = getNewKeyForCache()
            keyStores[currentGuide] = newCacheKey
            cacheKeyStore.add(newCacheKey)
            newCacheKey
        }

        return keyForCache
    }


    protected open fun getNewKeyForCache(): String{
        return keyAdapter.getCacheKey()
    }

}