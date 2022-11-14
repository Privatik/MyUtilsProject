package com.io.navigation_common

internal data class PresenterBody(
    val presenter: UIPresenter,
    val clazzFactory: Class<out PresenterFactory>
)



