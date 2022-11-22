package com.example.machine

import kotlinx.coroutines.flow.*

fun <S: Any, E: Any> reducer(
    defaultState: S,
    buildReducerDSL: ReducerDSL<S, E>.() -> Unit
): Reducer<S, E>{
    val dsl = ReducerDSL<S, E>().apply(buildReducerDSL)
    val initStep = Step<S, E>(defaultState, null)

    return object : Reducer<S, E>{
        private val _effects = MutableSharedFlow<E>()
        override val effects: Flow<E> = _effects

        override val state: Flow<S>
            get() {
                return dsl.transactions
                    .map { it.flow.map { payload -> Body(payload, it) } }
                    .merge()
                    .scan(initStep){ oldStep, body ->
                        val newState = body.transaction.changeState.get(oldStep.state, body.payload)
                        val effect: E? = body.transaction.get(oldStep.state, newState, body.payload)
                        Step(newState, effect)
                    }.transform<Step<S, E>, S> {
                        emit(it.state)
                        it.effect?.run { _effects.emit(this) }
                    }
                    .onStart{ dsl.defaultAction() }
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

interface Reducer<S: Any, E: Any>{


    val state: Flow<S>
    val effects: Flow<E>
}