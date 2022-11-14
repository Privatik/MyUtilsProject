package com.io.navigation_common

interface RestorePresenterStoreOwner<Key: Any>{
    fun saveSharedPresenters(): List<Pair<Key, Set<String>>>
    fun restoreSharedPresenters(retain: List<Pair<Key, Set<String>>>)

    fun saveTagPresenters(): List<Pair<Key, String>>
    fun restoreTagPresenters(retain: List<Pair<Key, String>>)
}

internal class DefaultRestorePresenterStoreOwner<Key: Any>(
    private val tagPresenterStore: TagPresenterStore<Key>,
    private val sharedPresenterStore: SharedPresenterStore<Key>
): RestorePresenterStoreOwner<Key>{

    override fun saveSharedPresenters(): List<Pair<Key, Set<String>>> =
        sharedPresenterStore.save()
    override fun restoreSharedPresenters(retain: List<Pair<Key, Set<String>>>) =
        sharedPresenterStore.restore(retain)

    override fun saveTagPresenters(): List<Pair<Key, String>> =
        tagPresenterStore.save()
    override fun restoreTagPresenters(retain: List<Pair<Key, String>>) =
        tagPresenterStore.restore(retain)

}

