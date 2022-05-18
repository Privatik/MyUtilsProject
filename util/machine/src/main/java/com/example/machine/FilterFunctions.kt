package com.example.machine

fun <S: Any, P: Any> RenderState<S,P>?.get(): RenderState<S,P>{
    return this ?: { S, _ -> S}
}

fun <S: Any, P: Any> DoAction<S, P>?.get(): DoAction<S, P>{
    return this ?: { _, _, _ -> }
}