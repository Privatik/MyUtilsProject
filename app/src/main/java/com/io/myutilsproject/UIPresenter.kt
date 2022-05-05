package com.io.myutilsproject

import com.io.navigation.UIPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class Presenter(
): UIPresenter {

    private val workScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private val testScope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())

    private var isTest: Boolean = false

    val presenterScope: CoroutineScope
    get() = if (isTest) testScope else workScope

    init {
        isTest = try {
            Class.forName("org.junit.runner.Runner")
            true
        } catch (ignored: ClassNotFoundException) {
            false
        }
    }

    final override fun clear() {
        onClear()
        presenterScope.cancel()
    }

    open fun onClear() {}
}