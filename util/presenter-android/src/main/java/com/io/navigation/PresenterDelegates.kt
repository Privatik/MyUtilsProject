package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import com.io.navigation_common.emptyPresenter

@Composable
public inline fun <reified P: UIPresenter> presenter(
    factory: PresenterFactory? = null,
    tag: String? = null,
): P {
    return presenter(tag, factory ?: emptyPresenter(), P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> sharedPresenter(
    factory: PresenterFactory? = null
): P {
    return presenter(null,factory ?: emptyPresenter(), P::class.java, true)
}

@Composable
public fun <P : UIPresenter> presenter(
    tag: String? = null,
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkNotNull(LocalPresenterKeyAdapter.current)

    val owner = LocalPresenterOwnerController.current

    return rememberSaveable(
        saver = owner.androidPresenterSaver<P>(
            tag = tag,
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    ) {
        owner.createPresenter<P>(
            tag = tag,
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    }
}
