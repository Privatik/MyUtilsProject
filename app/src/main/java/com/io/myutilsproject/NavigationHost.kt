package com.io.myutilsproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHost(){
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Screens.FirstScreen.route){

        controller.popBackStack()
        composable(Screens.FirstScreen.route){

        }

        composable(Screens.SecondScreen.route){

        }
    }
}

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
}