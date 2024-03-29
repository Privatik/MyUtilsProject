package com.example.machine

import kotlinx.coroutines.flow.Flow
import java.util.*

internal typealias RenderState<S, P> = (oldState: S, payload: P) -> S
internal typealias DoAction<S, P> = suspend (oldState: S, newState: S, payload: P) -> Unit
internal typealias GetEffect<S, P, E> = suspend (oldState: S, newState: S, payload: P) -> E?

class ReducerDSL<S: Any, E: Any> internal constructor(){
    internal val transactions = LinkedList<Transaction<S, out Any>>()

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
        val transaction = TransactionAction(
            everyFlow,
            updateState,
            action
        )
        transactions.add(transaction)
    }

    @JvmName("onEachEffect")
    fun <P: Any> onEach(
        everyFlow: Flow<P>,
        updateState: RenderState<S, P>? = null,
        effect: GetEffect<S, P, E>? = null
    ){
        val transaction = TransactionGetEffect(
            everyFlow,
            updateState,
            effect
        )
        transactions.add(transaction)
    }
}