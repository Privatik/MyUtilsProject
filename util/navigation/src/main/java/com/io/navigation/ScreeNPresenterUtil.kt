package com.io.navigation

import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilder
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem

fun <Presenter: Any> RootComposeBuilder.screenWithPresenter(
    name: String,
    content: RenderWithParams<Any?>
) {
    screen(name, content)
}

fun <Presenter: Any> RootComposeBuilder.flowWithPresenter(
    name: String,
    presenter: Presenter,
    builder: FlowBuilder.() -> Unit
) {
    flow(name, builder)
}

fun <Presenter: Any> FlowBuilder.screenWithPresenter(
    name: String,
    presenter: Presenter,
    content: RenderWithParams<Any?>
) {
    screen(name, content)
}

fun <Presenter: Any> MultiStackBuilder.tabWithPresenter(
    tabItem: TabItem,
    presenter: Presenter,
    builder: FlowBuilder.() -> Unit
) {
    tab(tabItem, builder)
}