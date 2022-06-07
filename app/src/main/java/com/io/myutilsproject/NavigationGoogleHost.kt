package com.io.myutilsproject

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen
import com.io.navigation.AdapterPresenter
import com.io.navigation.UpdatePresenter
import com.io.navigation.adapter
import com.io.navigation.presenter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun Navigation(
    navController: NavHostController,
){
    NavHost(
        navController = navController,
        startDestination = Screens.FirstScreen.route){

        composable(Screens.FirstScreen.route){
            FirstScreen{
                navController.navigate(Screens.SecondScreen.route)
            }
        }

        composable(Screens.SecondScreen.route){
            UpdatePresenter(::createAppComponent) {
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
                    inc = { secondPresenter.inc(state.value.count) },
                    incGod = { secondPresenter.incGod(state.value.godCount) },
                    open = {
                        navController.navigate(Screens.ThirdScreen.route)
                    }
                )
            }
        }

        composable(Screens.ThirdScreen.route){
            UpdatePresenter(::createNextComponent) {
                val thirdPresenter: ThirdPresenter = presenter()
                val state = thirdPresenter.state.collectAsState()
                val adapter = adapter<GooglePresenter>()

                TripleScreen(
                    state = state.value,
                    inc = { thirdPresenter.inc(state.value.count) },
                    backToFirst = {
                        navController.popBackStack(Screens.FirstScreen.route, false)
                        adapter.pop()
                    },
                    next = {

                    }
                )
            }
        }

    }
}