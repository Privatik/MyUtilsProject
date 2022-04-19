package com.io.navigation

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class PresenterFactory<Presenter: Any>(
    private val _presenterStore: HashMap<String, KClass<out Presenter>>
) {
    private val store = HashMap<String, Presenter>()

    fun <P: Presenter> create(key: String): Presenter {
        if (store.contains(key)){
            return store[key]!!
        }
        checkNotNull (_presenterStore[key]){
            "Presenter don't attach"
        }
        val presenter = _presenterStore[key]!!.createInstance()
        store[key] = presenter
        return presenter
    }
}