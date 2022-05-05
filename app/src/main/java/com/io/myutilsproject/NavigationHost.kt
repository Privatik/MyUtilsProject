package com.io.myutilsproject

import androidx.compose.runtime.collectAsState
import com.io.navigation.*
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.generateGraph() {

    screen(
        name = Screens.FirstScreen.route,
    ) {
        val controller = LocalRootController.current.asPresenterController()
        FirstScreen{
            controller.push(Screens.SecondScreen.route)
        }
    }

    screen(
        name = Screens.SecondScreen.route,
    ){
        val controller = LocalRootController.current.asPresenterController()
        val secondPresenter: SecondPresenter = presenter()
        val state = secondPresenter.count.collectAsState()
        SecondScreen(
            state = state.value,
            inc = secondPresenter::inc,
            open = {
                controller.pushAndCreateScope(
                    screen = Screens.TripleScreen.route,
                    createScope = ::createNextScope
                )
            }
        )
    }

    screen(
        name = Screens.SecondScreen.route,
    ){
        val thriplePresenter: ThriplePresenter = presenter()
        val state = thriplePresenter.count.collectAsState()
        TripleScreen(
            state = state.value,
            inc = thriplePresenter::inc,
        )
    }
}

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
    object TripleScreen: Screens("triple")
}