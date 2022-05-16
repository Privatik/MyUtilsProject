package com.io.navigation

interface PresenterFactory{

    fun <P: UIPresenter> create(model: Class<out UIPresenter>): P
}

class EmptyPresenterFactory: PresenterFactory{
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        throw NoSuchMethodException("Not PresenterFactory")
    }
}