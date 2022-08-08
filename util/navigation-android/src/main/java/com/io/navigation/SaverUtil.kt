package com.io.navigation

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner

internal fun PresenterOwnerSaver(): Saver<AndroidPresenterStoreOwner, *> =
    Saver(
        save = { it.saveState() },
        restore = { AndroidPresenterStoreOwner().apply { restoreState(it) } }
    )