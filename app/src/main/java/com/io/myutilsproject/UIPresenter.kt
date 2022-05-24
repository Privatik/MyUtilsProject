package com.io.myutilsproject

import com.example.machine.MachineDSL
import com.example.machine.machine
import com.io.navigation.UIPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class Presenter<S: Any, I: Any, E: Any> constructor(initState: S): UIPresenter {

    private val presenterScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _state = MutableStateFlow<S>(initState)
    val state = _state.asStateFlow()

    private val _singleEffect = MutableSharedFlow<E>()
    val singleEffect = _singleEffect.asSharedFlow()

    protected val incFlow = MutableSharedFlow<Int>()

    fun inc(count: Int){
        presenterScope.launch {
            incFlow.emit(count + 1)
        }
    }

    protected abstract val buildMachine: MachineDSL<S, E>.() -> Unit

    init {
        val machine = machine<S, E>(initState, ::initAction) { buildMachine() }
        machine.state
            .onEach {
                _state.emit(it)
            }
            .launchIn(presenterScope)
    }

    protected open suspend fun initAction(state: S) {

    }

    suspend fun sendSingleEffect(effect: E){
        _singleEffect.emit(effect)
    }

    final override fun clear() {
        presenterScope.cancel()
    }
}