package com.io.myutilsproject

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.io.myutilsproject.screens.fifth.FifthPresenter
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.fouth.FourthScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.second.SecondState
import com.io.myutilsproject.screens.sixth.SixthPresenter
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.ThirdState
import com.io.myutilsproject.screens.third.TripleScreen
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
//            val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
            FirstScreen{
                navController.navigate(Screens.SecondScreen.route)
            }
        }

        composable(Screens.SecondScreen.route){
            println("Presenter create second screen")
//            val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
//            val secondPresenter: SecondPresenter = presenter(appScopePresenter.factory)
//            val fifthPresenter: FifthPresenter = sharedPresenter()
//            val snackbarHostState = remember {
//                SnackbarHostState()
//            }
//
//            LaunchedEffect(Unit){
//                secondPresenter
//                    .singleEffect
//                    .onEach {
//                        when (it){
//                            is SecondEffect.Snack -> {
//                                println("Machine show toast with ${it.message}")
//                            }
//                        }
//                    }
//                    .launchIn(this)
//            }

            val secondState = remember {
                mutableStateOf(SecondState(godCount = 0))
            }
            val fifthState = remember {
                mutableStateOf(ThirdState())
            }
            SecondScreen(
                body =  secondState,
                bodyT = fifthState,
                inc = {  },
                incGod = { },
                open = {
                    navController.navigate(Screens.ThirdScreen.route)
                },
                incTag = {

                }
            )

        }

        composable(Screens.ThirdScreen.route){
//            val nextScopePresenter: SharedNextComponentPresenter = sharedPresenter()
//            val thirdPresenter: ThirdPresenter = presenter(nextScopePresenter.factory)
//            val sixthPresenter: SixthPresenter = sharedPresenter()

            val sixthState = remember {
                mutableStateOf(ThirdState())
            }
            val thirdState = remember {
                mutableStateOf(ThirdState())
            }
            TripleScreen(
                state = thirdState,
                stateT = sixthState,
                inc = {  },
                backToFirst = {
                    navController.popBackStack(Screens.FirstScreen.route, false)
                },
                next = {

                },
                incT = {

                }
            )

        }

        composable(Screens.FourScreen.route){
            FourthScreen()
        }

    }
}