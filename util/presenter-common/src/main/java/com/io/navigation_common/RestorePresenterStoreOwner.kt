package com.io.navigation_common

interface RestorePresenterStoreOwner<CacheKey: Any>{
    fun saveSharedPresenters(): Map<String, CacheKey>
    fun restoreSharedPresenters(retain: Map<String, CacheKey>)
}

internal class DefaultRestorePresenterStoreOwner<CacheKey: Any>(
    private val sharedPresenterStore: SharedPresenterStore<CacheKey>
): RestorePresenterStoreOwner<CacheKey>{

    override fun saveSharedPresenters(): Map<String, CacheKey> =
        sharedPresenterStore.save()
    override fun restoreSharedPresenters(retain: Map<String, CacheKey>) =
        sharedPresenterStore.restore(retain)

}

