package com.io.navigation

class Config internal constructor(items: HashMap<String?, ConfigItem>){
    internal val items = HashMap<String?, ConfigItem>()

    init {
        this.items.putAll(items)
    }

    fun set(item: Pair<String, () -> PresenterFactory>){
        items[item.first] = ConfigItem(item.second)
    }

    internal fun get(key: String): PresenterFactory{
        return items[key]!!.factory
    }
}

internal class ConfigItem(
    factory: () -> PresenterFactory
){
    val factory: PresenterFactory by lazy(factory)
}