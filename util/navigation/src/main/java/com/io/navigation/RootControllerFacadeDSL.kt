package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.alexgladkov.odyssey.compose.RenderWithParams
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import kotlin.reflect.KClass

val testFactory = object : PresenterFactory{
    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        TODO("Not yet implemented")
    }
}

typealias RenderWithPresenter
        = @Composable (
    Map<KClass<out UIPresenter>, UIPresenter>,
    RenderWithParams<Any?>
) -> Unit

data class MVIModel(
    val state: Any
    val intent: Any
)

fun renderWithPresenters(
    presenterMap: Map<KClass<out UIPresenter>, UIPresenter>,
    contentPresenter: RenderWithPresenter,
    content: RenderWithParams<Any?>
    ): RenderWithParams<Any?>{
    return {
        contentPresenter(presenterMap)
        content(it)
    }
}

fun RootComposeBuilder.screenWithAnotherPresenter(
    name: String,
    vararg presenters: KClass<out UIPresenter>,
    content: RenderWithParams<Any?>
) {

    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}

fun <P: UIPresenter> RootComposeBuilder.screen(
    name: String,
    content: RenderWithParams<Any?>
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}

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

//fun RootComposeBuilder.screenWithPresenters(
//    name: String,
//    vararg presenters: KClass<out UIPresenter>,
//    content: RenderWithPresenterAndParams<Any?>
//) {
//    addScreen(
//        key = name,
//        screenMap = hashMapOf(name to content)
//    )
//}

fun RootComposeBuilder.screenWithPresenterFactory(
    name: String,
    presenterFactory:() -> PresenterFactory,
    content: RenderWithParams<Any?>
) {
    addScreen(
        key = name,
        screenMap = hashMapOf(name to content)
    )
}
