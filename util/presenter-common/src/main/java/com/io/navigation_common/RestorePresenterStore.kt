package com.io.navigation_common

interface RestorePresenterStore<CacheKey: Any>{
    fun saveSharedPresenters(): Map<String, CacheKey>
    fun restoreSharedPresenters(retain: Map<String, CacheKey>)
}

internal class DefaultRestorePresenterStore<CacheKey: Any>(
    private val sharedPresenterStore: SharedPresenterStore<CacheKey>
): RestorePresenterStore<CacheKey>{

    override fun saveSharedPresenters(): Map<String, CacheKey> =
        sharedPresenterStore.save()
    override fun restoreSharedPresenters(retain: Map<String, CacheKey>) =
        sharedPresenterStore.restore(retain)

}

