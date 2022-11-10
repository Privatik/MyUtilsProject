package com.io.navigation

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.launchIn

fun <Key: Any> PresenterKeyAdapter<Key>.setupBackHandler(activity: ComponentActivity){
    activity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    })
}


@Composable
fun <Key: Any> PresenterCompositionLocalProvider(
    vararg providers: ProvidedValue<*>,
    controller: PresenterKeyAdapter<Key>,
    owner: PresenterStoreOwner<Key>,
    canUpdate: Boolean = true,
    canSaveStateKey: Boolean = true,
    content: @Composable () -> Unit
) {

    if (canUpdate) {
        LaunchedEffect(Unit){
            controller
                .updateScreen()
                .launchIn(this)
        }
    }


    val ownerProvider = if (canSaveStateKey){
        checkOwnerAsAndroidPresenterOwner(owner)
        val newOwner = rememberSaveable(saver = presenterOwnerSaver(owner)) { owner }
        LocalPresenterOwnerController provides newOwner
    } else {
        LocalPresenterOwnerController provides owner
    }

    CompositionLocalProvider(
        LocalPresenterKeyAdapter provides controller,
        ownerProvider,
        *providers
    ) {
        content()
    }
}

fun PresenterComponentActivity.setContentWithPresenter(
    vararg providers: ProvidedValue<*>,
    controller: PresenterKeyAdapter<String>,
    canUpdate: Boolean = true,
    canSaveStateKey: Boolean = true,
    content: @Composable () -> Unit
){
    setContent {
        PresenterCompositionLocalProvider(
            providers = providers,
            controller = controller,
            owner = presenterStoreOwner,
            canUpdate = canUpdate,
            canSaveStateKey = canSaveStateKey,
            content = content
        )
    }
}