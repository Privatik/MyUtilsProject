package com.io.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.launchIn

@Composable
fun <Key: Any> PresenterCompositionLocalProvider(
    vararg providers: ProvidedValue<*>,
    controller: PresenterController<Key>,
    owner: PresenterStoreOwner<Key>,
    canUpdate: Boolean = true,
    canSaveStateKey: Boolean = false,
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
        require(owner is AndroidPresenterStoreOwner) {
            "Not correct extends, use AndroidPresenterStoreOwner as parent for owner"
        }
        
        val newOwner = rememberSaveable(saver = PresenterOwnerSaver()) { owner }
        LocalPresenterOwnerController provides newOwner
    } else {
        LocalPresenterOwnerController provides owner
    }

    CompositionLocalProvider(
        LocalPresenterController provides controller,
        ownerProvider,
        *providers
    ) {
        content()
    }
}

fun PresenterComponentActivity.setContentWithPresenter(
    vararg providers: ProvidedValue<*>,
    controller: PresenterController<String>,
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