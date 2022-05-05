package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.io.navigation.LocalNavigationFactory
import com.io.navigation.buildWithPresenter
import com.io.navigation.setupWithActivity
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootComposeBuilder()
            .apply { generateGraph() }
            .buildWithPresenter(
                presenterFactory = appComponent().factory
            )
        rootController.setupWithActivity(this)

        setContent {
            CompositionLocalProvider(
                LocalRootController provides rootController
            ) {

                ModalNavigator {
                    Navigator(Screens.FirstScreen.route)
                }
            }
        }

    }
}