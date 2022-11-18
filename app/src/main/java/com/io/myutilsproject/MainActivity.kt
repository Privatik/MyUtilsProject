package com.io.myutilsproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.bumble.appyx.navmodel.backstack.BackStack
import com.io.navigation.PresenterCompositionLocalProvider

class MainActivity: NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setOdessey()
//        setGoogle()
        setAppyx()
    }

    private fun setAppyx() {

        setContent {
            NodeHost(integrationPoint = appyxIntegrationPoint) { buildContext ->
                AppyxHost(buildContext = buildContext)
            }
        }
    }

    fun setOdessey(){
//        val rootController = RootComposeBuilder()
//            .apply {
//                generateGraph()
//            }
//            .build()

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
            val owner = remember {
                GoogleOwnerPresenter(
                    GooglePresenterAdapter(
                        controller = navController,
                        subscribeOnDestroyState = {

                        }
                    )
                )
            }

            PresenterCompositionLocalProvider(
                owner = owner
            ) {
                Navigation(
                    navController = navController,
                    owner
                )
            }
        }
    }
}