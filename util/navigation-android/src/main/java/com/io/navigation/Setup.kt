package com.io.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import com.io.navigation_common.PresenterController
import kotlinx.coroutines.flow.launchIn

@Composable
fun <Key: Any> PresenterCompositionLocalProvider(
    vararg providers: ProvidedValue<*>,
    controller: PresenterController<Key>,
    canUpdate: Boolean = true,
    content: @Composable () -> Unit
) {

    if (canUpdate) {
        LaunchedEffect(Unit){
            controller
                .updateScreen()
                .launchIn(this)
        }
    }

    CompositionLocalProvider(
        LocalPresenterController provides controller,
        *providers
    ) {
        content()
    }
}

fun <Key: Any> PresenterComponentActivity<Key>.setContentWithPresenter(
    controller: PresenterController<Key>,
    canUpdate: Boolean = true,
    content: @Composable () -> Unit
){
    setContent {
        PresenterCompositionLocalProvider(
            controller = controller,
            canUpdate = canUpdate,
            content = content
        )
    }
}