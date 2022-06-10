package com.io.navigation

internal data class SharedPresenterBody(
    val count: Int = 1,
    val presenter: UIPresenter,
    val clazzFactory: Class<out PresenterFactory>
)

internal data class PresenterBody(
    val presenter: UIPresenter,
    val clazzFactory: Class<out PresenterFactory>
)



