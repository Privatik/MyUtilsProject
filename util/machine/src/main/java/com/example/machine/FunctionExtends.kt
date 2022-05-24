package com.example.machine

internal fun <S: Any> RenderState<S, Any>?.get(oldState: S, payload: Any): S{
    val funState = this ?: { S, _ -> S}
    return funState(oldState, payload)
}

@JvmName("doAction")
internal suspend fun <S,P: Any> DoAction<S, P>?.get(oldState: S, newState: S, payload: P){
    val funAction = this ?: { _, _, _ -> }
    funAction(oldState, newState, payload)
}

@JvmName("getEffect")
internal suspend fun <S, P, E: Any> GetEffect<S, P, E>?.get(oldState: S, newState: S, payload: P): E?{
    val funAction = this ?: { _, _, _ -> null}
    return funAction(oldState, newState, payload)
}