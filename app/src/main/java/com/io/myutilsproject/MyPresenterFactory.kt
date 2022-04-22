package com.io.myutilsproject

import com.io.navigation.PresenterFactory
import com.io.navigation.UIPresenter
import javax.inject.Inject
import javax.inject.Provider

class MyPresenterFactory @Inject constructor(
    private val presenterFactories: Map<Class<out Presenter>, @JvmSuppressWildcards Provider<Presenter>>
): PresenterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <P : UIPresenter> create(model: Class<P>): P {
        return presenterFactories.getValue(model as Class<Presenter>).get() as P
    }

}