package com.io.navigation_common

import java.util.concurrent.ConcurrentHashMap

internal class SimplePresenterStore()  {
    private val screenWithPresenterMap = HashMap<Class<out UIPresenter>, PresenterBody>()

    fun <P: UIPresenter> createOrGetPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (screenWithPresenterMap.containsKey(clazz)){
            println("Presenter-simple get ${screenWithPresenterMap[clazz]}")
            @Suppress("UNCHECKED_CAST")
            return screenWithPresenterMap[clazz]!!.presenter as P
        } else {
            val presenter = factory.create<P>(clazz)
            screenWithPresenterMap[clazz] = PresenterBody(presenter, factory::class.java)
            println("Presenter-simple create ${screenWithPresenterMap[clazz]}")
            presenter.build()
            return presenter
        }
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( clazz, _ ) ->
            screenWithPresenterMap.remove(clazz)!!.apply {
                println("Presenter-simple remove all $this")
                presenter.clear()
            }
        }
    }
}