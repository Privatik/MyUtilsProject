package com.io.myutilsproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.io.myutilsproject.screens.fifth.FifthPresenter
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.fouth.FourthScreen
import com.io.myutilsproject.screens.second.SecondEffect
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.third.ThirdPresenter
import com.io.myutilsproject.screens.third.TripleScreen
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <Guide: Any> Navigation(
    navController: NavHostController,
    presenterStoreOwner: PresenterStoreOwner<Guide>
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
            val fifthPresenter: FifthPresenter = sharedPresenter()

            LaunchedEffect(Unit){
                secondPresenter
                    .singleEffect
                    .onEach {
                        when (it){
                            is SecondEffect.Snack -> {
                                println("Machine show toast with ${it.message}")
                            }
                        }
                    }
                    .launchIn(this)
            }

            SecondScreen(
                body =  secondPresenter.state.collectAsState(),
                bodyT = fifthPresenter.state.collectAsState(),
                inc = { secondPresenter.inc(it + 1) },
                incGod = { fifthPresenter.inc(it + 1) },
                open = {
                    navController.navigate(Screens.ThirdScreen.route)
                },
                incTag = {

                }
            )

        }

        composable(Screens.ThirdScreen.route){
            val nextScopePresenter: SharedNextComponentPresenter = sharedPresenter()
            val thirdPresenter: ThirdPresenter = presenter(nextScopePresenter.factory)
            val fifthPresenter: FifthPresenter = sharedPresenter()

            TripleScreen(
                state = thirdPresenter.state.collectAsState(),
                stateT = fifthPresenter.state.collectAsState(),
                inc = { fifthPresenter.inc(it + 1) },
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