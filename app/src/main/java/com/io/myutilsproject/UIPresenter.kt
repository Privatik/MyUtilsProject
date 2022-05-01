package com.io.myutilsproject

import com.io.navigation.UIPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class Presenter: UIPresenter {

    val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun clear() {
        onClear()
        presenterScope.cancel()
    }

    open fun onClear() {}
}