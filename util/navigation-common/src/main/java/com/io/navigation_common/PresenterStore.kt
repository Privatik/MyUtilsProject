package com.io.navigation_common

import java.util.concurrent.ConcurrentHashMap

internal class PresenterStore(private val factoryObjectCounter: FactoryObjectCounter)  {
    private val screenWithPresenterMap = HashMap<Class<out UIPresenter>, PresenterBody>()

    fun <P: UIPresenter> createOrGetPresenter(
        itemKey: String?,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (screenWithPresenterMap.containsKey(clazz)){
            writeMessage("Get Presenter $clazz")
            @Suppress("UNCHECKED_CAST")
            return screenWithPresenterMap[clazz] as P
        } else {
            val presenter = factory.create<P>(clazz)
            writeMessage("Add Presenter $clazz")
            screenWithPresenterMap[clazz] = PresenterBody(presenter, factory::class.java)

            factoryObjectCounter.addObject(itemKey, factory::class.java)

            presenter.build()
            return presenter
        }
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( clazz, _ ) ->
            screenWithPresenterMap.remove(clazz)!!.apply {
                presenter.clear()
                factoryObjectCounter.removeObject(clazzFactory)
            }
            writeMessage("delete $clazz presenter")
        }
    }
}