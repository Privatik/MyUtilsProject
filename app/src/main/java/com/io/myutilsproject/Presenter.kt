package com.io.myutilsproject

import com.example.machine.ReducerDSL
import com.example.machine.reducer
import com.io.navigation.AndroidPresenter
import com.io.navigation_common.UIPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class Presenter<S: Any, I: Any, E: Any>(
    initBody: Pair<S, suspend () -> Unit>
) : AndroidPresenter() {

    val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableStateFlow<S>(initBody.first)
    val state: StateFlow<S> by lazy(LazyThreadSafetyMode.NONE) {
        build(initBody)
        _state.asStateFlow()
    }

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    protected abstract fun ReducerDSL<S, E>.machine()

    private fun build(initBody: Pair<S, suspend () -> Unit>){
        reducer<S, E>(initBody.first, initBody.second) { machine() }.apply {
            state
                .onEach {
                    _state.emit(it)
                }.launchIn(presenterScope)

            effects
                .onEach {
                    _singleEffect.emit(it)
                }.launchIn(presenterScope)
        }
    }

    final override fun clear() {
        presenterScope.cancel()
    }
}