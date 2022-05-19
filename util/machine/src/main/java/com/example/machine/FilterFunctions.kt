package com.example.machine

internal fun <S: Any> RenderState<S>?.get(oldState: S, payload: Any): S{
    val funState = this ?: { S, _ -> S}
    return funState(oldState, payload)
}

internal suspend fun <S: Any> DoAction<S>?.get(oldState: S, newState: S, payload: Any){
    val funAction = this ?: { _, _, _ -> }
    funAction(oldState, newState, payload)
}