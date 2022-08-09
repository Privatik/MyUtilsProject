package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.SavedStateHandle
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter
import com.io.navigation_common.emptyPresenter

@Composable
public inline fun <reified P: UIPresenter> presenter(
    factory: PresenterFactory? = null,
): P {
    return presenter(factory ?: emptyPresenter(), P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> sharedPresenter(
    factory: PresenterFactory? = null
): P {
    return presenter(factory ?: emptyPresenter(), P::class.java, true)
}

@Composable
public fun <P : UIPresenter> presenter(
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkNotNull(LocalPresenterController.current)

    val owner = LocalPresenterOwnerController.current
    val localInfo = LocalInfoAboutWorkLibrary.current

    return if (localInfo.isUseSaveState)
        getPresenterWithSave(owner = owner, factory = factory, clazz = clazz, isShared = isShared)
    else
        getPresenterWithOutSave(owner = owner, factory = factory, clazz = clazz, isShared = isShared)
}

@Composable
private fun <P: UIPresenter> getPresenterWithOutSave(
    owner: PresenterStoreOwner<out Any>,
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P = remember {

    owner.createPresenter<P>(
        clazz = clazz,
        factory = factory,
        isShared = isShared
    )
}

@Composable
private fun <P: AndroidPresenter> getPresenterWithSave(
    owner: PresenterStoreOwner<out Any>,
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkOwnerAsAndroidPresenterOwner(owner)

    return rememberSaveable(
        saver = AndroidPresenterSaver<P>(
            owner = owner,
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    ) {
        owner.createPresenter<P>(
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    }
}

