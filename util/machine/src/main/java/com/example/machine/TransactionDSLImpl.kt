package com.example.machine

import kotlinx.coroutines.flow.Flow

class TransactionAction<S: Any, P: Any> internal constructor(
    everyFlow: Flow<P>
): TransactionDSL<S,P>(everyFlow){
    internal var actionBody: DoAction<S,P>? = null

    fun action(body: DoAction<S,P>){
        check(actionBody == null){
            "\"action\" can only one"
        }
        actionBody = body
    }
}

class TransactionGetEffect<S: Any, P: Any, E: Any> internal constructor(
    everyFlow: Flow<P>
): TransactionDSL<S,P>(everyFlow){
    internal var effectBody: GetEffect<S,P,E>? = null

    fun effect(body: GetEffect<S,P,E>){
        check(effectBody == null){
            "\"effect\" can only one"
        }
        effectBody = body
    }
}
