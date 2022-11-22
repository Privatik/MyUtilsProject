package com.io.myutilsproject

import com.example.machine.ReducerDSL
import com.example.machine.reducer
import com.io.navigation.AndroidPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

abstract class Presenter<S: Any, I: Any, E: Any>(
    defaultState: S
): AndroidPresenter() {

    val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableStateFlow<S>(defaultState)
    val state: StateFlow<S> by lazy(LazyThreadSafetyMode.NONE){
        build()
        _state.asStateFlow()
    }

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    protected abstract fun ReducerDSL<S, E>.reducer()

    private fun build(){
        reducer<S, E>(defaultState = _state.value) { reducer() }.apply {
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