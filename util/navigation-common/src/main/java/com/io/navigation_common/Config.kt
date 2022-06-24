package com.io.navigation_common

class Config internal constructor(){
    private val items = HashMap<String, ConfigItem>()
    private val cacheItems = HashMap<String, () -> PresenterFactory>()

    fun put(
        itemKey: String,
        factory: () -> PresenterFactory
    ){
        if (itemKey.isBlank()) error("Blank is reserved itemKey")

        if (!items.contains(itemKey)){
            items[itemKey] = ConfigItem(factory)
            cacheItems[itemKey] = factory
        }
    }

    fun releaseFull(itemKey: String){
        release(itemKey)
        cacheItems.remove(itemKey)
    }

    internal fun release(itemKey: String){
        if (itemKey.isBlank()) error("Blank is reserved itemKey")
        items.remove(itemKey)
    }

    internal fun get(itemKey: String?): PresenterFactory {
        if(itemKey == null) return EmptyPresenterFactory()
        return items[itemKey]?.factory ?: kotlin.run {
            val factory = cacheItems[itemKey]
                ?: error("Don't found cache function for create factory, Maybe you use to \"releaseFull\" before")
            val newConfigItem = ConfigItem(factory)
            items[itemKey] = newConfigItem
            newConfigItem.factory
        }
    }
}

private class ConfigItem(
    factory: () -> PresenterFactory
){
    val factory: PresenterFactory by lazy(LazyThreadSafetyMode.NONE, factory)
}