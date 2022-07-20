package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import com.io.navigation_common.emptyPresenter

@Composable
public inline fun <reified P: UIPresenter> presenter(
    factory: PresenterFactory = emptyPresenter()
): P {
    return presenter(factory, P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> sharedPresenter(
    factory: PresenterFactory = emptyPresenter()
): P {
    return presenter(factory, P::class.java, true)
}

@Composable
public fun <P : UIPresenter> presenter(
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkNotNull(LocalPresenterController.current)

    val owner = LocalPresenterOwnerController.current

    return remember {
        owner.createPresenter(
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    }
}
