package com.io.navigation_common

interface RestorePresenterStoreOwner<Key: Any>{
    fun saveInfoAboutTag(): Map<String, Key>
    fun restoreInfoAboutTag(retainKeys: Map<String, Key>)
    fun saveInfoAboutShared(): Map<String, HashSet<Key>>
    fun restoreInfoAboutShared(retainKeys: Map<String, HashSet<Key>>)
    fun saveInfoAboutBackStack(): List<Key>
    fun restoreInfoAboutBackStack(retainKeys: List<Key>){ }
}

internal class DefaultRestorePresenterStoreOwner<Key: Any>(
    private val presenterBackStack: PresenterBackStack<Key>,
    private val keyTag: HashMap<String, Key>,
    private val sharedPresenterStore: SharedPresenterStore<Key>
): RestorePresenterStoreOwner<Key>{

    override fun saveInfoAboutTag(): Map<String, Key>{
        return keyTag
    }

    override fun restoreInfoAboutTag(retainKeys: Map<String, Key>){
        if (keyTag.isEmpty()){
            keyTag.putAll(retainKeys)
        }
    }

    override fun saveInfoAboutShared(): Map<String, HashSet<Key>>{
        return sharedPresenterStore.save()
    }

    override fun restoreInfoAboutShared(retainKeys: Map<String, HashSet<Key>>){
        sharedPresenterStore.restore(retainKeys)
    }

    override fun saveInfoAboutBackStack(): List<Key>{
        return presenterBackStack.saveBackStack()
    }

    override fun restoreInfoAboutBackStack(retainKeys: List<Key>){
        presenterBackStack.restoreBackStack(retainKeys)
    }

}

