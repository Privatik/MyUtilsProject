package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import com.io.navigation.LocalPresenterFactory
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.setupNavigation
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setupNavigation(Screens.FirstScreen.route){
//            generateGraph()
//        }

        val rootController = RootComposeBuilder().apply { generateGraph() }.build()
        rootController.setupWithActivity(this)

        setContent {
            CompositionLocalProvider(
                LocalRootController provides rootController,
                LocalPresenterFactory provides (applicationContext as App).appComponent.factory
            ) {
//                val currentScreen = LocalRootController.current
//                LaunchedEffect(Unit){
//                    currentScreen.currentScreen.collect{
//                        Timber.d("CurrentScreen ${it.screen.key}")
//                    }
//                }

                ModalNavigator {
                    Navigator(Screens.FirstScreen.route)
                }
            }
        }

    }
}