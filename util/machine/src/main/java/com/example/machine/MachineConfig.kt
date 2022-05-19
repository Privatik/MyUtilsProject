package com.example.machine

import kotlinx.coroutines.flow.*

fun <S: Any> machine(
    initState: S,
    buildMachineDSL: MachineDSL<S>.() -> Unit
): Machine<S>{
    val dsl = MachineDSL<S>().apply(buildMachineDSL)
    return object : Machine<S>{
        override val state: MutableStateFlow<S> = MutableStateFlow<S>(dsl.startState)

        init {
            dsl.transactions
                .map { it.everyFlow.map { payload -> payload to it } }
                .merge()
                .scan(initState){ oldState, transaction ->
                    val newState = transaction.second.render.get(oldState, transaction.first)
                    transaction.second.actionBody.get(oldState, newState, transaction.first)
                    newState
                }.transform {
                    emit(it)
                }
        }
    }
}

interface Machine<S: Any>{
    val state: Flow<S>
}