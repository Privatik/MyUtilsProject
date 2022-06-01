package com.io.navigation

internal data class ScreenInfo<Key: Any>(
    val screen: Key,
    val presenterFactory: PresenterFactory
)

internal data class PresenterBody(
    val clazz: Class<out UIPresenter>,
    val presenter: UIPresenter
)

internal data class SharedPresenterBody(
    val count: Int = 1,
    val presenter: UIPresenter
)



