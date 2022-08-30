package com.example.machine

import kotlinx.coroutines.flow.*

fun <S: Any, E: Any> reducer(
    initState: S,
    inItAction: suspend () -> Unit = {},
    buildReducerDSL: ReducerDSL<S, E>.() -> Unit
): Machine<S, E>{
    val initStep = Step<S, E>(initState, null)

    val dsl = ReducerDSL<S, E>().apply(buildReducerDSL)

    return object : Machine<S, E>{
        private val _effects = MutableSharedFlow<E>()
        override val effects: Flow<E> = _effects

        override val state: Flow<S>
            get() {
                return dsl.transactions
                    .map { it.everyFlow.map { payload -> Body(payload, it) } }
                    .merge()
                    .scan(initStep){ oldStep, body ->
                        val newState = body.transaction.render.get(oldStep.state, body.payload)
                        val effect: E? = body.transaction.get(oldStep.state, newState, body.payload)
                        Step(newState, effect)
                    }.transform<Step<S, E>, S> {
                        emit(it.state)
                        it.effect?.run { _effects.emit(this) }
                    }
                    .onStart{
                        inItAction()
                    }
            }
    }
}

internal data class Step<S: Any, E: Any>(
    val state: S,
    val effect: E?
)

internal data class Body<S: Any, P : Any>(
    val payload: P,
    val transaction: Transaction<S, out Any>
)

interface Machine<S: Any, E: Any>{
    val state: Flow<S>
    val effects: Flow<E>
}