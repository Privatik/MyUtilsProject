package com.io.navigation

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter

internal fun PresenterOwnerSaver(): Saver<AndroidPresenterStoreOwner, *> =
    Saver(
        save = { it.saveState() },
        restore = { AndroidPresenterStoreOwner().apply { restoreState(it) } }
    )

internal fun <P : AndroidPresenter> AndroidPresenterSaver(
    owner: AndroidPresenterStoreOwner,
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean
): Saver<P, *> =
    Saver(
        save = { it.save() },
        restore = { bundle ->
            owner.createPresenter<P>(
                clazz = clazz,
                factory = factory,
                isShared = isShared
            ).also { it.restore(bundle) }
        }
    )