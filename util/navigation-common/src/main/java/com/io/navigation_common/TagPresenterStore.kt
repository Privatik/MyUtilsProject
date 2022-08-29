package com.io.navigation_common

class TagPresenterStore(
    val tag: String
) {
    private val presenterBody = HashMap<Class<out UIPresenter>, PresenterBody>()

    fun <P: UIPresenter> createOrGet(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        return if (presenterBody.containsKey(clazz)){
            getPresenter(clazz)
        } else {
            createPresenter(clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ):P{
        val presenter = factory.create<P>(clazz)
        presenterBody[clazz] = PresenterBody(presenter, factory::class.java)
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        clazz: Class<out UIPresenter>
    ):P{
        @Suppress("UNCHECKED_CAST")
        return presenterBody[clazz]!!.presenter as P
    }

    fun clear(){
        presenterBody.forEach { (_, presenterBody) ->
            presenterBody.presenter.clear()
        }
        presenterBody.clear()
    }
}