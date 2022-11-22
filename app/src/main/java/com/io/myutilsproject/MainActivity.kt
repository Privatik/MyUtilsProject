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

        setGoogle()
//        setAppyx()
    }

//    private fun setAppyx() {
//
//        setContent {
//            NodeHost(integrationPoint = appyxIntegrationPoint) { buildContext ->
//                AppyxHost(buildContext = buildContext)
//            }
//        }
//    }

    fun setGoogle(){

        setContent {
            val navController = rememberNavController()
            val owner = remember {
                GoogleOwnerPresenter(
                    GooglePresenterAdapter(
                        controller = navController
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