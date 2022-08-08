package com.io.myutilsproject

import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import javax.inject.Inject
import javax.inject.Provider

class MyPresenterFactory @Inject constructor(
    private val presenterFactories: Map<Class<out Presenter<*,*,*>>, @JvmSuppressWildcards Provider<Presenter<*,*,*>>>
): com.io.navigation_common.PresenterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
         return presenterFactories.getValue(model as Class<out Presenter<*,*,*>>).get() as P
    }

    val map = presenterFactories
}