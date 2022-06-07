package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.io.navigation.PresenterCompositionLocalProvider
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import ru.alexgladkov.odyssey.compose.setupNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val rootController = RootComposeBuilder()
//            .apply {
//                generateGraph()
//            }
//            .build()
//
//        val adapter = OdesseyPresenter(rootController)
//        rootController.setupWithActivity(
//            this,
//            adapter
//        )

        setContent {
            val navController = rememberNavController()
            val adapter = GooglePresenter(navController)

            BackHandler(
                onBack = {
                    println("Presenter ${navController.backQueue.size}")
                    if (navController.backQueue.size <= 2){
                        finish()
                    } else {
                        navController.popBackStack()
                        adapter.pop()
                    }
                }
            )

            PresenterCompositionLocalProvider(
                adapter = adapter
            ) {

                Navigation(
                    navController = navController
                )
            }
        }

    }
}