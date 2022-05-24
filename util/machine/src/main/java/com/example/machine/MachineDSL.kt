package com.example.machine

import kotlinx.coroutines.flow.Flow

internal typealias RenderState<S, P> = (oldState: S, payload: P) -> S
internal typealias DoAction<S, P> = suspend (oldState: S, newState: S, payload: P) -> Unit
internal typealias GetEffect<S, P, E> = suspend (oldState: S, newState: S, payload: P) -> E?

class MachineDSL<S: Any, E: Any> internal constructor(){
    internal val transactions = mutableListOf<Transaction<S, out Any>>()

    fun <P: Any> onEach(
        everyFlow: Flow<P>,
        updateState: RenderState<S, P>,
    ){
        onEach(
            everyFlow = everyFlow,
            updateState = updateState,
            action = null
        )
    }

    @JvmName("onEachAction")
    fun <P: Any> onEach(
        everyFlow: Flow<P>,
        updateState: RenderState<S, P>? = null,
        action: DoAction<S, P>? = null
    ){
        val transaction = TransactionAction<S,P>(everyFlow).apply {
            state(updateState)
            action(action)
        }
        transactions.add(transaction)
    }


    @JvmName("onEachEffect")
    fun <P: Any> onEach(
        everyFlow: Flow<P>,
        updateState: RenderState<S, P>? = null,
        effect: GetEffect<S, P, E>? = null
    ){
        val transaction = TransactionGetEffect<S,P,E>(everyFlow).apply {
            state(updateState)
            effect(effect)
        }
        transactions.add(transaction)
    }
}