package com.example.machine

import kotlinx.coroutines.flow.Flow
import java.util.*
import kotlin.properties.Delegates

internal typealias ChangeStateFunc<S, P> = (oldState: S, payload: P) -> S
internal typealias DoActionFunc<S, P> = suspend (oldState: S, newState: S, payload: P) -> Unit
internal typealias GetEffectFromActionFunc<S, P, E> = suspend (oldState: S, newState: S, payload: P) -> E?

class ReducerDSL<S: Any, E: Any> internal constructor(){
    var defaultAction: suspend () -> Unit = {}

    internal val transactions = LinkedList<Transaction<S, out Any>>()

    fun <P: Any> onEach(
        flow: Flow<P>,
        changeState: ChangeStateFunc<S, P>,
    ){
        onEach(
            flow = flow,
            changeState = changeState,
            action = { _, _, _ -> }
        )
    }

    @JvmName("onEachAction")
    fun <P: Any> onEach(
        flow: Flow<P>,
        changeState: ChangeStateFunc<S, P>? = null,
        action: DoActionFunc<S, P>? = null
    ){
        val transaction = TransactionWithAction(
            flow,
            changeState,
            action
        )
        transactions.add(transaction)
    }

    @JvmName("onEachEffect")
    fun <P: Any> onEach(
        flow: Flow<P>,
        changeState: ChangeStateFunc<S, P>? = null,
        effect: GetEffectFromActionFunc<S, P, E>? = null
    ){
        val transaction = TransactionWithEffect(
            flow,
            changeState,
            effect
        )
        transactions.add(transaction)
    }
}