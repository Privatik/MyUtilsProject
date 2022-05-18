package com.io.myutilsproject

import com.example.machine.Machine
import com.example.machine.MachineDSL
import com.example.machine.machine
import com.io.navigation.UIPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class Presenter<S: Any, I: Any, E: Any>: UIPresenter {

    private val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableSharedFlow<S>(replay = 1)
    val state = _state.asSharedFlow()

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    abstract val Machine<S>.buildMachine: MachineDSL<S>.() -> Unit

    init {
        @Suppress("LeakingThis")
        val machine = machine<S>().apply { buildMachine() }
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
        onClear()
        presenterScope.cancel()
    }

    open fun onClear() {}
}