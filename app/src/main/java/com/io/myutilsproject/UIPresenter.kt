package com.io.myutilsproject

import com.example.machine.MachineDSL
import com.example.machine.machine
import com.io.navigation.UIPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class Presenter<S: Any, I: Any, E: Any> constructor(private val initState: S): UIPresenter {

    val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableStateFlow<S>(initState)
    val state = _state.asStateFlow()

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    protected abstract fun machine(): MachineDSL<S, E>.() -> Unit

    protected open suspend fun initAction(state: S) = Unit

    final override fun build() {
        val machine = machine<S, E>(initState, ::initAction, machine())
        machine.state
            .onEach {
                _state.emit(it)
            }
            .launchIn(presenterScope)

        machine.effects
            .onEach {
                _singleEffect.emit(it)
            }.launchIn(presenterScope)
    }

    final override fun clear() {
        presenterScope.cancel()
    }
}