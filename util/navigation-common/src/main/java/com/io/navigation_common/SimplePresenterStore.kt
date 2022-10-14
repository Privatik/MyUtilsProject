package com.io.navigation_common

import java.util.concurrent.ConcurrentHashMap

internal class SimplePresenterStore()  {
    private val screenWithPresenterMap = HashMap<Class<out UIPresenter>, PresenterBody>()

    fun <P: UIPresenter> createOrGetPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (screenWithPresenterMap.containsKey(clazz)){
            @Suppress("UNCHECKED_CAST")
            return getPresenter(clazz)
        } else {
            return createPresenter(clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ):P{
        val presenter = factory.create<P>(clazz)
        screenWithPresenterMap[clazz] = PresenterBody(presenter, factory::class.java)
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        clazz: Class<out UIPresenter>
    ):P{
        @Suppress("UNCHECKED_CAST")
        return screenWithPresenterMap[clazz]!!.presenter as P
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( _, presenterBody ) ->
            presenterBody.presenter.clear()
        }
        screenWithPresenterMap.clear()
    }
}