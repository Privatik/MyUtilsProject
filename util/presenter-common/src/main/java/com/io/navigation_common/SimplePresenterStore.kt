package com.io.navigation_common

internal class SimplePresenterStore {
    private val screenWithPresenterMap = HashMap<Class<out UIPresenter>, UIPresenter>()

    fun <P: UIPresenter> getPresenter(
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
        screenWithPresenterMap[clazz] = presenter
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        clazz: Class<out UIPresenter>
    ):P{
        @Suppress("UNCHECKED_CAST")
        return screenWithPresenterMap[clazz]!! as P
    }

    fun clear(){
        screenWithPresenterMap.forEach { ( _, presenter ) ->
            presenter.clear()
        }
        screenWithPresenterMap.clear()
    }
}