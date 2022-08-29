package com.io.myutilsproject

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.io.myutilsproject.screens.fifth.FifthPresenter
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.fouth.FourthPresenter
import com.io.myutilsproject.screens.fouth.FourthScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.sixth.SixthPresenter
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen

import com.io.navigation.presenterController
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
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
            println("Presenter create first screen")
            val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
            FirstScreen{
                navController.navigate(Screens.SecondScreen.route)
            }
        }

        composable(Screens.SecondScreen.route){
            println("Presenter create second screen")
            val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
            val secondPresenter: SecondPresenter = presenter(appScopePresenter.factory)
            val fifthPresenter: FifthPresenter = presenter(tag = "SHARED")
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
                body = secondPresenter.state.collectAsState(),
                bodyT = fifthPresenter.state.collectAsState(),
                inc = { secondPresenter.inc(it) },
                incGod = { secondPresenter.incGod(it) },
                open = {
                    navController.navigate(Screens.ThirdScreen.route)
                },
                incTag = {
                    fifthPresenter.inc(it)
                }
            )

        }

        composable(Screens.ThirdScreen.route){
            val nextScopePresenter: SharedNextComponentPresenter = sharedPresenter()
            println("Presenter create third screen")
            val thirdPresenter: ThirdPresenter = presenter(nextScopePresenter.factory)
            val sixthPresenter: SixthPresenter = presenter(tag = "SHARED")
            val adapter = presenterController<GooglePresenterController>()

            TripleScreen(
                state = thirdPresenter.state.collectAsState(),
                stateT = sixthPresenter.state.collectAsState(),
                inc = { thirdPresenter.inc(it) },
                backToFirst = {
                    navController.popBackStack(Screens.FirstScreen.route, false)
                },
                next = {

                },
                incT = {
                    sixthPresenter.inc(it)
                }
            )

        }

        composable(Screens.FourScreen.route){
            FourthScreen()
        }

    }
}