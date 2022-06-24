package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.io.navigation_common.UIPresenter

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
    checkNotNull(LocalPresenterController.current)

    val controller = LocalPresenterController.current

    return remember {
        val factory = controller.config.get(key)
        val owner = controller.owner

        if (isShared){
            owner.createOrGetSharedPresenter<P>(clazz, factory)
        } else {
            val store = owner.createOrGetPresenterStore()
            store.createOrGetPresenter<P>(clazz, factory)
        }
    }
}
