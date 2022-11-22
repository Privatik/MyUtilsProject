package com.example.machine

import kotlinx.coroutines.flow.Flow

internal interface Transaction<S: Any, P: Any> {
    val flow: Flow<P>
    val changeState: ChangeStateFunc<S,P>?
}

internal data class TransactionWithAction<S: Any, P: Any>(
    override val flow: Flow<P>,
    override val changeState: ChangeStateFunc<S,P>? = null,
    internal var action: DoActionFunc<S,P>? = null
): Transaction<S, P>

internal data class TransactionWithEffect<S: Any, P: Any, E: Any>(
    override val flow: Flow<P>,
    override val changeState: ChangeStateFunc<S,P>? = null,
    internal val effect: GetEffectFromActionFunc<S,P,E>? = null
): Transaction<S,P>