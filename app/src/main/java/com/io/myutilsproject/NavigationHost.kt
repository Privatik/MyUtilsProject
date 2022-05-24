package com.io.myutilsproject

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen
import com.io.navigation.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        val snackbarHostState = remember {
            SnackbarHostState()
        }

        LaunchedEffect(Unit){
            secondPresenter
                .singleEffect
                .onEach {
                    when (it){
                        is SecondEffect.Snack -> {
                            snackbarHostState.showSnackbar(
                                message = it.message,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                }
                .launchIn(this)
        }

        SecondScreen(
            state = state.value,
            inc = { secondPresenter.inc(state.value.count + 1) },
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
            inc = { thirdPresenter.inc(state.value.count + 1) },
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