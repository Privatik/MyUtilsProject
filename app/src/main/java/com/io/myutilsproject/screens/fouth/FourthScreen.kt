package com.io.myutilsproject.screens.fouth

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.io.myutilsproject.Screens
import com.io.myutilsproject.screens.eight.EighthPresenter
import com.io.myutilsproject.screens.fifth.FifthScreen
import com.io.myutilsproject.screens.seventh.SeventhPresenter
import com.io.myutilsproject.screens.seventh.SeventhScreen
import com.io.myutilsproject.screens.sixth.SixthScreen
import com.io.myutilsproject.screens.third.ThirdState
import com.io.navigation.presenter

private val navigationList = listOf<Pair<String, Screens>>(
    "five" to Screens.SeventhScreen,
    "six" to Screens.SixthScreen
)

private val tag = "SHARED"

@Composable
fun FourthScreen() {
    var currentScreen by remember {
        mutableStateOf<Screens>(Screens.SeventhScreen)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(9f)
    ) {
        when (currentScreen){
            Screens.SeventhScreen -> {
                val presenter: SeventhPresenter by presenter(tag = tag)

                SeventhScreen(presenter.state.collectAsState().value){

                }
            }
            Screens.SixthScreen -> {
                val presenter: EighthPresenter by presenter(tag = tag)

                SixthScreen(
                    state = presenter.state.collectAsState().value,
                    inc = {

                    },
                    open = {}
                )
            }
            else -> error("Not withScreen")
        }

    }

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth(1f),
        backgroundColor = Color.Black,
        contentColor = Color.Green
    ) {
        navigationList.forEach {
            BottomNavigationItem(
                selected = it.second == currentScreen,
                onClick = {
                     currentScreen = it.second
                },
                icon = {},
                label = {
                    Text(text = it.first)
                }
            )
        }
    }

}