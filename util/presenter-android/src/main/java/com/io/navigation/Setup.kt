package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.io.navigation_common.PresenterStoreOwner

@Composable
fun <Guide: Any> PresenterCompositionLocalProvider(
    vararg providers: ProvidedValue<*>,
    owner: PresenterStoreOwner<Guide>,
    canSaveStateKey: Boolean = true,
    content: @Composable () -> Unit
) {

    val ownerProvider = if (canSaveStateKey){
        checkOwnerAsAndroidPresenterOwner(owner)
        val newOwner = rememberSaveable(saver = presenterOwnerSaver(owner)) { owner }
        LocalPresenterOwnerController provides newOwner
    } else {
        LocalPresenterOwnerController provides owner
    }

    CompositionLocalProvider(
        ownerProvider,
        *providers
    ) {
        content()
    }
}