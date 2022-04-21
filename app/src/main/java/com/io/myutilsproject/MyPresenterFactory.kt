package com.io.myutilsproject

import com.io.navigation.PresenterFactory
import javax.inject.Inject
import javax.inject.Provider

class MyPresenterFactory @Inject constructor(
    private val presenterFactories: Map<Class<out Presenter>, @JvmSuppressWildcards Provider<Presenter>>
): PresenterFactory<Presenter> {

    @Suppress("UNCHECKED_CAST")
    override fun <P : Presenter> create(model: Class<P>): Presenter {
        return  presenterFactories.getValue(model as Class<Presenter>).get() as P
    }

}