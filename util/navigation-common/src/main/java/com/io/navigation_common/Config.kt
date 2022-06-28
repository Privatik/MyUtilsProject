package com.io.navigation_common

class Config internal constructor(){
    private val items = HashMap<String, PresenterFactory>()
    private val cacheItems = HashMap<String, () -> PresenterFactory>()

    fun put(
        itemKey: String,
        factory: () -> PresenterFactory
    ){
        if (itemKey.isBlank()) error("Blank is reserved itemKey")

        if (!items.contains(itemKey)){
            cacheItems[itemKey] = factory
        }
    }

    fun releaseFull(itemKey: String){
        stop(itemKey)
        cacheItems.remove(itemKey)
    }

    internal fun stop(itemKey: String){
        if (itemKey.isBlank()) error("Blank is reserved itemKey")
        items.remove(itemKey)
    }

    internal fun get(itemKey: String?): PresenterFactory {
        if(itemKey == null) return EmptyPresenterFactory()
        return items[itemKey] ?: kotlin.run {
            val factoryInstance = cacheItems[itemKey]?.invoke()
                ?: error("Don't found cache function for create factory, Maybe you use to \"releaseFull\" before")
            items[itemKey] = factoryInstance
            factoryInstance
        }
    }
}