package com.io.navigation

import androidx.compose.runtime.saveable.Saver
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter

internal fun <Guide: Any, CacheKey: Any> presenterOwnerSaver(
    owner: AndroidPresenterStoreOwner<Guide, CacheKey>
): Saver<AndroidPresenterStoreOwner<Guide, CacheKey>, *> =
    Saver(
        save = { it.saveState() },
        restore = { owner.apply { restoreState(it) } }
    )

internal fun <P: UIPresenter, Guide: Any, CacheKey: Any> PresenterStoreOwner<Guide, CacheKey>.androidPresenterSaver(
    tag: String? = null,
    factory: PresenterFactory,
    clazz: Class<out UIPresenter>,
    isShared: Boolean
): Saver<P, *> =
    Saver(
        save = { if (it is AndroidPresenter) it.save() else null },
        restore = { bundle ->
           createPresenter<P>(
                tag = tag,
                clazz = clazz,
                factory = factory,
                isShared = isShared
            ).also {
                if (it is AndroidPresenter){
                    it.restore(bundle)
                }
            }
        }
    )