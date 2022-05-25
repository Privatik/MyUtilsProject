package com.example.machine

internal fun <S: Any, P: Any> RenderState<S, P>?.get(oldState: S, payload: Any): S{
    val funState = this ?: { S, _ -> S}
    @Suppress("UNCHECKED_CAST")
    return funState(oldState, payload as P)
}

@JvmName("doAction")
internal suspend inline fun <S: Any, P: Any> DoAction<S, P>?.get(oldState: S, newState: S, payload: Any){
    val funAction = this ?: { _, _, _ -> }
    @Suppress("UNCHECKED_CAST")
    funAction(oldState, newState, payload as P)
}

@JvmName("getEffect")
internal suspend inline fun <S: Any, P: Any, E: Any> GetEffect<S, P, E>?.get(oldState: S, newState: S, payload: Any): E?{
    val funAction = this ?: { _, _, _ -> null}
    @Suppress("UNCHECKED_CAST")
    return funAction(oldState, newState, payload as P)
}