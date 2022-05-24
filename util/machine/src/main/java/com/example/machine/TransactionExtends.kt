package com.example.machine

internal suspend fun<S: Any, P: Any, E: Any> Transaction<S, P>.get(oldState: S, newState: S, payload: P): E?{
    return if (this.isAction()){
        this.asAction().actionBody.get(oldState, newState, payload)
        null
    } else {
        this.asEffect<S, P, E>().effectBody.get(oldState, newState, payload)
    }
}

internal fun <S: Any, P: Any> Transaction<S, P>.isAction(): Boolean{
    return this is TransactionAction<S, P>
}

internal fun <S: Any, P: Any> Transaction<S, P>.asAction(): TransactionAction<S, P>{
    return this as TransactionAction<S, P>
}

internal fun <S: Any, P: Any, E: Any> Transaction<S,P>.asEffect(): TransactionGetEffect<S, P, E>{
    @Suppress("UNCHECKED_CAST")
    return this as TransactionGetEffect<S, P, E>
}