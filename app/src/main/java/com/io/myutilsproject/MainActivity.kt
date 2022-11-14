package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.io.navigation.DefaultPresenterComponent
import com.io.navigation.PresenterComponent
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation_common.PresenterStoreOwner
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

class MainActivity: NodeActivity(),
    PresenterComponent<NavBackStackEntry, String> by DefaultPresenterComponent() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setOdessey()
        setGoogle()
//        setAppyx()
    }

    private fun setAppyx() {
//        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//
//            }
//        })
//
//        setContent {
//            PresenterCompositionLocalProvider<String>(){
//                NodeHost(integrationPoint = appyxIntegrationPoint) {
//                    AppyxHost(it, lifecycleScope)
//                }
//            }
//        }
    }

    fun setOdessey(){
        val rootController = RootComposeBuilder()
            .apply {
                generateGraph()
            }
            .build()

//        val controller = OdesseyPresenterController(rootController, presenterStoreOwner)
//        rootController.setupWithActivity(
//            this,
//            controller
//        )
//
//        setContentWithPresenter(
//            LocalRootController provides rootController,
//            controller = controller,
//            canUpdate = false
//        ) {
//            var flag by remember { mutableStateOf(UUID.randomUUID()) }
//            LaunchedEffect(flag){
//                controller
//                    .updateScreen()
//                    .launchIn(this)
//
//                merge(
//                    controller.updateControllerFlow  ,
//                    controller.updateParentControllerFlow
//                )
//                    .onEach {
//                        flag = UUID.randomUUID()
//                    }
//                    .launchIn(this)
//
//            }
//
//            ModalNavigator {
//                Navigator(Screens.FirstScreen.route)
//            }
//        }
    }

    fun setGoogle(){

        setContent {
            val navController = rememberNavController()
            remember {
                GooglePresenterKeyAdapter(navController)
                    .also { adapter -> createPresenterOwner(this, getOwner(adapter)) }
            }
            BackHandler(
                onBack = {
                    if (navController.backQueue.size <= 2){
                        finish()
                    } else {
                        navController.popBackStack()
                    }
                }
            )

            PresenterCompositionLocalProvider(
                owner = retainPresenterStoreOwner
            ) {
                Navigation(
                    navController = navController
                )
            }
        }
    }

    private fun getOwner(adapter: GooglePresenterKeyAdapter): PresenterStoreOwner<NavBackStackEntry, String>{
        return GooglePresenterOwner(adapter)
    }
}