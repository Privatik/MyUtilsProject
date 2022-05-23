package com.example.machine

import kotlinx.coroutines.flow.*

fun <S: Any, E: Any> machine(
    initState: S,
    intiAction: suspend (state: S) -> Unit,
    buildMachineDSL: MachineDSL<S, E>.() -> Unit
): Machine<S, E>{
    val initPair = Pair<S, E?>(initState, null)

    val dsl = MachineDSL<S, E>().apply(buildMachineDSL)

    return object : Machine<S, E>{
        private val _effects = MutableSharedFlow<E>()
        override val effects: Flow<E> = _effects

        override val state: Flow<S>
            get() {
                return dsl.transactions
                    .map { it.everyFlow.map { payload -> payload to it } }
                    .merge()
                    .scan(initPair){ oldPair, transaction ->
                        val newState = transaction.second.render.get(oldPair.first, transaction.first)
                        val effect = transaction.second.get<S, Any, E>(oldPair.first, newState, transaction.first)
                        newState to effect
                    }.transform {
                        emit(it.first)
                        it.second?.run {
                            _effects.emit(this)
                        }
                    }.onStart {
                        intiAction(initState)
                    }
            }
    }
}

interface Machine<S: Any, E: Any>{
    val state: Flow<S>
    val effects: Flow<E>
}