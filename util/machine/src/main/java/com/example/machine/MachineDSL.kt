package com.example.machine

import kotlinx.coroutines.flow.Flow
import kotlin.properties.Delegates

internal typealias RenderState<S> = (oldState: S, payload: Any) -> S
internal typealias DoAction<S> = suspend (oldState: S, newState: S, payload: Any) -> Unit

class MachineDSL<S: Any> internal constructor(){
    internal val transactions = mutableListOf<TransactionDSL<S, out Any>>()

    var startState: S by Delegates.notNull()
    var startAction: (suspend () -> Unit)? = null

    fun <P: Any> onEach(everyFlow: Flow<P>, body: TransactionDSL<S,P>.() -> Unit){
        val transaction = TransactionDSL<S,P>(everyFlow).apply(body)
        transactions.add(transaction)
    }
}

class TransactionDSL<S: Any, P: Any> internal constructor(
    internal val everyFlow: Flow<P>
){
    internal var render: RenderState<S>? = null
    internal var actionBody: DoAction<S>? = null

    fun state(body: RenderState<S>){
        check(render == null){
            "\"state\" can only one"
        }
        render = body
    }

    fun action(body: DoAction<S>){
        check(actionBody == null){
            "\"action\" can only one"
        }
        actionBody = body
    }
}