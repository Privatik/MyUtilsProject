package com.example.machine

import kotlinx.coroutines.flow.Flow

internal abstract class Transaction<S: Any, P: Any> constructor(
    internal val everyFlow: Flow<P>
){
    internal var render: RenderState<S,P>? = null

    fun state(body: RenderState<S,P>?){
        render = body
    }
}

internal class TransactionAction<S: Any, P: Any>  constructor(
    everyFlow: Flow<P>
): Transaction<S,P>(everyFlow){
    internal var actionBody: DoAction<S,P>? = null

    fun action(body: DoAction<S,P>?){
        actionBody = body
    }
}

internal class TransactionGetEffect<S: Any, P: Any, E: Any> constructor(
    everyFlow: Flow<P>
): Transaction<S,P>(everyFlow){
    internal var effectBody: GetEffect<S,P,E>? = null

    fun effect(body: GetEffect<S,P,E>?){
        effectBody = body
    }
}
