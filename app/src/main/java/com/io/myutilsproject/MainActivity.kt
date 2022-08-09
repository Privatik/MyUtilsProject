package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.io.navigation.PresenterComponentActivity
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.setContentWithPresenter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator
import java.util.*

class MainActivity : PresenterComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setOdessey()
        setGoogle()

    }

    fun setOdessey(){
        val rootController = RootComposeBuilder()
            .apply {
                generateGraph()
            }
            .build()

        val controller = OdesseyPresenterController(rootController, presenterStoreOwner)
        rootController.setupWithActivity(
            this,
            controller
        )

        setContentWithPresenter(
            LocalRootController provides rootController,
            controller = controller,
            canUpdate = false
        ) {
            var flag by remember { mutableStateOf(UUID.randomUUID()) }
            LaunchedEffect(flag){
                controller
                    .updateScreen()
                    .launchIn(this)

                merge(
                    controller.updateControllerFlow  ,
                    controller.updateParentControllerFlow
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

    fun setGoogle(){

        setContent {
            val navController = rememberNavController()
            val controller = GooglePresenterController(navController, presenterStoreOwner)

            BackHandler(
                onBack = {
                    if (navController.backQueue.size <= 2){
                        finish()
                    } else {
                        navController.popBackStack()
                        controller.clearDontUsePresenter()
                    }
                }
            )

            PresenterCompositionLocalProvider(
                controller = controller,
                owner = presenterStoreOwner,
                canSaveStateKey = false
            ) {
                Navigation(
                    navController = navController
                )
            }
        }
    }
}