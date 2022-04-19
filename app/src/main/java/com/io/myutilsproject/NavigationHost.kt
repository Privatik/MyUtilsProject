package com.io.myutilsproject

import com.io.navigation.RootControllerWithPresenterBuilder
import com.io.navigation.screen
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController


fun RootControllerWithPresenterBuilder<Presenter>.generateGraph() {

    screen(
        name = Screens.FirstScreen.route,
        presenter = FirstPresenter::class
    ) {
        val controller = LocalRootController.current
        FirstScreen{
            controller.push(Screens.SecondScreen.route)
        }
    }

    screen(
        name = Screens.SecondScreen.route,
        presenter = SecondPresenter::class
    ) {
        SecondScreen()
    }
}

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
}