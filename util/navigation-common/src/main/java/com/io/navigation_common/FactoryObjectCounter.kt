package com.io.navigation_common

import java.util.HashMap

internal class FactoryObjectCounter(private val config: Config) {
    private val factoryMap = HashMap<Class<out PresenterFactory>, ConfigFactory>()

    fun addObject(itemKey: String?, clazz: Class<out PresenterFactory>){
        val countFactories = factoryMap.initializeOrGet(
            clazz,
            ConfigFactory(itemKey ?: "", 0)
        )

        factoryMap[clazz] = countFactories.copy(count = countFactories.count + 1)
    }

    fun removeObject(clazz: Class<out PresenterFactory>){
        val countFactories = factoryMap[clazz]

        if (countFactories != null){
            if (countFactories.count == 1){
                factoryMap.remove(clazz)
                config.stop(countFactories.itemKey)
            } else {
                factoryMap[clazz] = countFactories.copy(count = countFactories.count - 1)
            }
        }
    }
}

private data class ConfigFactory(
    val itemKey: String,
    val count: Int
)