package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.*
import java.util.*


abstract class AdapterPresenter<Key: Any,Controller>(
    protected val controller: Controller,
    startPresenterFactory: () -> PresenterFactory = ::emptyPresenter
) {
    private val presenterController = PresenterController<Key>()

    abstract fun getCurrentScreen(): Key
    protected abstract fun getBackStack(): List<Key>

    fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        isShared: Boolean
    ): P {
        return if (isShared)
            presenterController.getSharedPresenter(
                key = getCurrentScreen(),
                clazz = clazz
            )
        else
            presenterController.getPresenter(
                key = getCurrentScreen(),
                clazz = clazz
            )
    }

    internal fun updateFactory(factory: () -> PresenterFactory){
        presenterController.updateFactory(getCurrentScreen(), factory)
    }

}