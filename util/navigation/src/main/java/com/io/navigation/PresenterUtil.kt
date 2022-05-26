package com.io.navigation

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.screen.Screen

fun emptyPresenter(): PresenterFactory{
    return EmptyPresenterFactory()
}


internal fun RootController.getBackStack(): List<Screen>{
    val backstackField = javaClass.getDeclaredField("_backstack")
    backstackField.isAccessible = true
    @Suppress("UNCHECKED_CAST")
    return backstackField.get(this) as MutableList<Screen>
}