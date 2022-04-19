package com.io.navigation

import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilderModel
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel
import kotlin.reflect.KClass

class RootControllerWithPresenterBuilder<Presenter: Any>(
    private val rootComposeBuilder: RootComposeBuilder
) {
    private val presenterStore = hashMapOf<String, KClass<out Presenter>>()

    fun addScreen(
        key: String,
        presenter: KClass<out Presenter>,
        screenMap: Map<String, RenderWithParams<Any?>>
    ) {
        rootComposeBuilder.addScreen(key, screenMap)
        presenterStore[key] = presenter
    }

    fun addFlow(
        key: String,
        flowBuilderModel: FlowBuilderModel,
    ) {
        rootComposeBuilder.addFlow(key, flowBuilderModel)
    }

    fun addMultiStack(
        key: String,
        tabsNavModel: TabsNavModel<*>,
        multiStackBuilderModel: MultiStackBuilderModel,
    ) {
        rootComposeBuilder.addMultiStack(key, tabsNavModel, multiStackBuilderModel)
    }

    fun build(): RootControllerFacade<Presenter> {
        return RootControllerFacade(
            rootComposeBuilder.build(),
            presenterStore
        )
    }
}