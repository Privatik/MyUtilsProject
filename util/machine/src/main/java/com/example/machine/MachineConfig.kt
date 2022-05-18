package com.example.machine

import kotlinx.coroutines.flow.*

fun <S: Any> machine(
    buildMachineDSL: MachineDSL<S>.() -> Unit
): Machine<S>{
    val dsl = MachineDSL<S>().apply(buildMachineDSL)
    return object : Machine<S>{
        override val state: Flow<S>
            get() = dsl.transactions
                .map { it.everyFlow }
                .scan()
    }
}

interface Machine<S: Any>{
    val state: Flow<S>
}

internal data class TransactionConfig<P: Any>(
    val everyFlow: Flow<P>,

)