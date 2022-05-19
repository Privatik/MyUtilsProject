package com.io.myutilsproject

import com.example.machine.MachineDSL
import com.example.machine.machine
import com.io.navigation.UIPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*

abstract class Presenter<S: Any, I: Any, E: Any> constructor(initState: S): UIPresenter {

    private val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableStateFlow<S>(initState)
    val state = _state.asStateFlow()

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    abstract val buildMachine: MachineDSL<S>.() -> Unit

    init {
        val machine = machine<S>(initState) { buildMachine() }
        machine.state
            .onEach {
                _state.emit(it)
            }
            .launchIn(presenterScope)
    }

    suspend fun sendSingleEffect(effect: E){
        _singleEffect.emit(effect)
    }

    final override fun clear() {
        presenterScope.cancel()
    }
}