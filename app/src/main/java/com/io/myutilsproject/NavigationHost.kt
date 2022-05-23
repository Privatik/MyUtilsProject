package com.io.myutilsproject

import androidx.compose.runtime.collectAsState
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen
import com.io.navigation.*
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
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
        val state = secondPresenter.state.collectAsState()

        SecondScreen(
            state = state.value,
            inc = secondPresenter::inc,
            open = {
                controller.push(
                    screen = Screens.ThirdScreen.route,
                    presenterFactory = ::createNextComponent
                )
            }
        )
    }

    screen(
        name = Screens.ThirdScreen.route,
    ){
        val controller = LocalRootController.current.asPresenterController()
        val thirdPresenter: ThirdPresenter = presenter()
        val state = thirdPresenter.state.collectAsState()
        TripleScreen(
            state = state.value,
            inc = thirdPresenter::inc,
            backToFirst = {
                controller.backToScreenWithPresenter(Screens.FirstScreen.route)
            }
        )
    }
}

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
    object ThirdScreen: Screens("triple")
}