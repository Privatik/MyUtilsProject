package com.example.machine

internal suspend fun<S: Any, P: Any, E: Any> Transaction<S, P>.get(oldState: S, newState: S, payload: Any): E?{
    return if (this.isAction()){
        this.asAction().action.get(oldState, newState, payload)
        null
    } else {
        this.asEffect<S, P, E>().effect.get(oldState, newState, payload)
    }
}

internal fun <S: Any, P: Any> Transaction<S, P>.isAction(): Boolean{
    return this is TransactionWithAction<S, P>
}

internal fun <S: Any, P: Any> Transaction<S, P>.asAction(): TransactionWithAction<S, P>{
    return this as TransactionWithAction<S, P>
}

internal fun <S: Any, P: Any, E: Any> Transaction<S,P>.asEffect(): TransactionWithEffect<S, P, E>{
    @Suppress("UNCHECKED_CAST")
    return this as TransactionWithEffect<S, P, E>
}