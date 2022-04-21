package com.io.myutilsproject

import com.io.navigation.LocalRootFacadeController
import com.io.navigation.push
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.generateGraph() {

    screen(
        name = Screens.FirstScreen.route,
    ) {
        val controller = LocalRootFacadeController.current
        FirstScreen{
            controller.push(Screens.SecondScreen.route)
        }
    }
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
}