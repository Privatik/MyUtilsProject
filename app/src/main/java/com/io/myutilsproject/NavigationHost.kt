package com.io.myutilsproject

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.io.navigation.*
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.generateGraph() {

    screen(
        name = Screens.FirstScreen.route,
    ) {
        val controller = LocalRootFacade.current
        FirstScreen{
            controller.push(Screens.SecondScreen.route)
        }
    }

    screen(
        name = Screens.SecondScreen.route,
    ){
        val secondPresenter: SecondPresenter = presenter()
        val state = secondPresenter.count.collectAsState()
        SecondScreen(
            state = state.value,
            inc = secondPresenter::inc
        )
    }

//    screenWithPresenters(
//        name = Screens.SecondScreen.route,
//        presenters = arrayOf(SecondPresenter::class)
//    ){ body, presenter ->
//
//    }

//    screen(
//        name = Screens.SecondScreen.route,
//    ){
//        CompositionLocalProvider(
//            LocalPresenterFactory provides
//        ) {
//            val secondPresenter: SecondPresenter = presenter()
//            val state = secondPresenter.count.collectAsState()
//            SecondScreen(
//                state = state.value,
//                inc = secondPresenter::inc
//            )
//        }
//    }

//    screenWithPresenters(
//        name = Screens.SecondScreen.route,
//        presenters = [SecondPresenter::class]
//    ){
//        SecondScreen()
//    }
//
//    screenWithPresenter(
//        name = Screens.SecondScreen.route,
//    ) {
//        SecondScreen()
//    }
}

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
    object TripleScreen: Screens("second")
}