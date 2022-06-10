package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.builder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOdessey()
//        setGoogle()

    }

    fun setOdessey(){
        val rootController = RootComposeBuilder()
            .apply {
                generateGraph()
            }
            .build()

        val config = builder {
            put(Constant.APP_FACTORY to ::createAppComponent)
        }

        val adapter = OdesseyPresenterAdapter(rootController, config)
        rootController.setupWithActivity(
            this,
            adapter
        )

        setContent {
            PresenterCompositionLocalProvider(
                LocalRootController provides rootController,
                adapter = adapter,
                canUpdate = false
            ) {
                var flag by remember { mutableStateOf(UUID.randomUUID()) }
                LaunchedEffect(flag){
                    adapter
                        .updateScreen()
                        .launchIn(this)

                    merge(
                        adapter.updateControllerFlow  ,
                        adapter.updateParentControllerFlow
                    )
                        .onEach {
                            flag = UUID.randomUUID()
                        }
                        .launchIn(this)

                }

                ModalNavigator {
                    Navigator(Screens.FirstScreen.route)
                }
            }
        }
    }

    fun setGoogle(){
        val config = builder {
            put(Constant.APP_FACTORY to ::createAppComponent)
        }

        setContent {
            val navController = rememberNavController()
            val adapter = GooglePresenterAdapter(navController,config)

            BackHandler(
                onBack = {
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