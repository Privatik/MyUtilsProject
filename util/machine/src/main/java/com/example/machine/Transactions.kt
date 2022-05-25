package com.example.machine

import kotlinx.coroutines.flow.Flow

internal interface Transaction<S: Any, P: Any> {
    val everyFlow: Flow<P>
    val render: RenderState<S,P>?
}

internal data class TransactionAction<S: Any, P: Any>(
    override val everyFlow: Flow<P>,
    override val render: RenderState<S,P>? = null,
    internal var actionBody: DoAction<S,P>? = null
): Transaction<S, P>

internal data class TransactionGetEffect<S: Any, P: Any, E: Any>(
    override val everyFlow: Flow<P>,
    override val render: RenderState<S,P>? = null,
    internal val effectBody: GetEffect<S,P,E>? = null
): Transaction<S,P>