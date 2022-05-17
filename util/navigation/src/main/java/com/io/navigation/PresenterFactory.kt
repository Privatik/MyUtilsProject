package com.io.navigation

interface PresenterFactory{

    fun <P: UIPresenter> create(model: Class<out UIPresenter>): P
}

class EmptyPresenterFactory: PresenterFactory{
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return model.newInstance() as P
    }
}