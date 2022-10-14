package com.io.navigation_common

interface PresenterFactory{

    fun <P: UIPresenter> create(model: Class<out UIPresenter>): P
}

class EmptyPresenterFactory: PresenterFactory {
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST", "DEPRECATION")
        return model.newInstance() as P
    }
}

class AssistedPresenterFactory(
    private val presenter: UIPresenter
): PresenterFactory {
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return presenter as P
    }

}

fun assistedPresenter(presenter: UIPresenter): PresenterFactory{
    return AssistedPresenterFactory(presenter)
}

fun emptyPresenter(): PresenterFactory {
    return EmptyPresenterFactory()
}

