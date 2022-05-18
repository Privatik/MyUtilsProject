package com.example.machine

import kotlinx.coroutines.flow.Flow
import kotlin.properties.Delegates

internal typealias RenderState<S, P> = (oldState: S, payload: P) -> S
internal typealias DoAction<S, P> = suspend (oldState: S, newState: S, payload: P) -> Unit

class MachineDSL<S: Any> internal constructor(){
    var initState: suspend () -> S by Delegates.notNull()
    internal val transactions = mutableListOf<TransactionDSL<S,out Any>>()

    fun <P: Any> onEach(everyFlow: Flow<P>, body: TransactionDSL<S,P>.() -> Unit){
        val transaction = TransactionDSL<S,P>(everyFlow).apply(body)
        transactions.add(transaction)
    }
}

class TransactionDSL<S: Any, P: Any> internal constructor(
    val everyFlow: Flow<P>
){
    var render: RenderState<S, P>? = null
    var actionBody: DoAction<S, P>? = null

    fun state(body: RenderState<S,P>){
        check(render == null){
            "\"state\" can only one"
        }
        render = body
    }

    fun action(body: DoAction<S,P>){
        check(actionBody == null){
            "\"action\" can only one"
        }
        actionBody = body
    }
}