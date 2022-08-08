package com.io.navigation_common

import java.util.concurrent.ConcurrentHashMap

internal class SimplePresenterStore()  {
    private val screenWithPresenterMap = HashMap<Class<out UIPresenter>, PresenterBody>()

    fun <P: UIPresenter> createOrGetPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (screenWithPresenterMap.containsKey(clazz)){
            writeMessage("Get Presenter $clazz")
            @Suppress("UNCHECKED_CAST")
            return screenWithPresenterMap[clazz]!!.presenter as P
        } else {
            val presenter = factory.create<P>(clazz)
            writeMessage("Add Presenter $clazz")
            screenWithPresenterMap[clazz] = PresenterBody(presenter, factory::class.java)

            presenter.build()
            return presenter
        }
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( clazz, _ ) ->
            screenWithPresenterMap.remove(clazz)!!.apply {
                presenter.clear()
            }
            writeMessage("delete $clazz presenter")
        }
    }
}