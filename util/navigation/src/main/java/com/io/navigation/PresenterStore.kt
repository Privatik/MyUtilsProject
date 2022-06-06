package com.io.navigation

import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class PresenterStore  {
    private val screenWithPresenterMap = ConcurrentHashMap<Class<out UIPresenter>, UIPresenter>()

    fun <P: UIPresenter> createOrGetPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (screenWithPresenterMap.containsKey(clazz)){
            @Suppress("UNCHECKED_CAST")
            return screenWithPresenterMap[clazz] as P
        } else {
            val presenter = factory.create<P>(clazz)
            screenWithPresenterMap[clazz] = presenter
            presenter.build()
            return presenter
        }
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( clazz, _ ) ->
            screenWithPresenterMap.remove(clazz)!!.clear()
        }
    }
}