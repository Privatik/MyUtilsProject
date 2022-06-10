package com.io.navigation

import java.util.concurrent.ConcurrentHashMap

class Config internal constructor(){
    private val items = ConcurrentHashMap<String?, ConfigItem>()

    fun put(item: Pair<String, () -> PresenterFactory>){
        if (item.first.isBlank()) return
        if (!items.contains(item.first)){
            items[item.first] = ConfigItem(item.second)
        }
    }

    fun release(itemKey: String){
        if (itemKey.isBlank()) return
        items.remove(itemKey)
    }

    internal fun get(key: String?): PresenterFactory{
        return items[key]?.factory ?: EmptyPresenterFactory()
    }
}

private class ConfigItem(
    factory: () -> PresenterFactory
){
    val factory: PresenterFactory by lazy(factory)
}