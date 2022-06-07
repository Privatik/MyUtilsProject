package com.io.myutilsproject

sealed class Screens(val route: String){
    object FirstScreen: Screens("first")
    object SecondScreen: Screens("second")
    object ThirdScreen: Screens("triple")
    object FourScreen: Screens("four")
    object FifthScreen: Screens("fifth")
    object SixthScreen: Screens("sixth")
    object SeventhScreen: Screens("seven")
}