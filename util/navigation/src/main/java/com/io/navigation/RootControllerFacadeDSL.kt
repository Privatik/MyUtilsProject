package com.io.navigation

import androidx.compose.runtime.CompositionLocalProvider
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TopNavConfiguration
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.animations.defaultPushAnimation
import kotlin.reflect.KClass

fun RootComposeBuilder.screenWithPresenters(
    name: String,
    vararg presenters: KClass<out UIPresenter>,
    content: RenderWithParams<Any?>
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}

fun RootComposeBuilder.screenWithPresenterFactory(
    name: String,
    content: RenderWithParams<Any?>
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}
