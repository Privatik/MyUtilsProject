package com.io.myutilsproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.io.myutilsproject.screens.first.FirstScreen
import com.io.myutilsproject.screens.second.SecondScreen
import com.io.myutilsproject.screens.third.TripleScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.FirstScreen.route){

        composable(Screens.FirstScreen.route){
            FirstScreen{

            }
        }

        composable(Screens.SecondScreen.route){
            SecondScreen(

            )
        }
        composable(Screens.ThirdScreen.route){
            TripleScreen(

            )
        }
        composable(Screens.FourScreen.route){

        }

    }
}