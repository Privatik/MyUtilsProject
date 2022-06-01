package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.io.navigation.buildWithPresenter
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.setupNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootComposeBuilder()
            .apply { generateGraph() }
            .build()

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