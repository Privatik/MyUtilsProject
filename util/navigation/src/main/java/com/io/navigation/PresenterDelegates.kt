package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
public inline fun <reified P: UIPresenter> presenter(key: String? = null): P {
    return presenter(key, P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> sharedPresenter(key: String? = null): P {
    return presenter(key, P::class.java, true)
}

@Composable
public fun <P : UIPresenter> presenter(
    key: String?,
    clazz: Class<out UIPresenter>,
    isShared: Boolean = false
): P {
    checkNotNull(LocalPresenterOwnerController.current)
    checkNotNull(LocalPresenterFactoryController.current)

    val factory = LocalPresenterFactoryController.current
    val owner = LocalPresenterOwnerController.current

    return remember {
        if (isShared){
            owner.createOrGetSharedPresenter<P>(clazz, factory)
        } else {
            val store = owner.createOrGetPresenterStore()
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }
}
