package com.example.machine

internal suspend fun<S: Any, P: Any, E: Any> TransactionDSL<S, P>.get(oldState: S, newState: S, payload: Any): E?{
    return if (this.isAction()){
        this.asAction().actionBody.get(oldState, newState, payload)
        null
    } else {
        this.asEffect<S, P, E>().effectBody.get(oldState, newState, payload)
    }
}

internal fun <S: Any, P: Any> TransactionDSL<S, P>.isAction(): Boolean{
    return this is TransactionAction<S, P>
}

internal fun <S: Any, P: Any> TransactionDSL<S, P>.asAction(): TransactionAction<S, P>{
    return this as TransactionAction<S, P>
}

internal fun <S: Any, P: Any, E: Any> TransactionDSL<S,P>.asEffect(): TransactionGetEffect<S, P, E>{
    @Suppress("UNCHECKED_CAST")
    return this as TransactionGetEffect<S, P, E>
}