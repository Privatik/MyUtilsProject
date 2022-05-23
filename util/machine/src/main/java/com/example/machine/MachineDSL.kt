package com.example.machine

import kotlinx.coroutines.flow.Flow
import kotlin.properties.Delegates

internal typealias RenderState<S, P> = (oldState: S, payload: P) -> S
internal typealias DoAction<S, P> = suspend (oldState: S, newState: S, payload: P) -> Unit
internal typealias GetEffect<S, P, E> = suspend (oldState: S, newState: S, payload: P) -> E

class MachineDSL<S: Any, E: Any> internal constructor(){
    internal val transactions = mutableListOf<TransactionDSL<S, out Any>>()

    fun <P: Any> onEach(everyFlow: Flow<P>, body: TransactionAction<S,P>.() -> Unit){
        val transaction = TransactionAction<S,P>(everyFlow).apply(body)
        transactions.add(transaction)
    }

//    @JvmName("onEachEffect")
//    fun <P: Any> onEach(everyFlow: Flow<P>, body: TransactionGetEffect<S,P,E>.() -> Unit){
//        val transaction = TransactionGetEffect<S,P,E>(everyFlow).apply(body)
//        transactions.add(transaction)
//    }
}

abstract class TransactionDSL<S: Any, P: Any> internal constructor(
    internal val everyFlow: Flow<P>
){
    internal var render: RenderState<S,P>? = null

    fun state(body: RenderState<S,P>){
        check(render == null){
            "\"state\" can only one"
        }
        render = body
    }
}