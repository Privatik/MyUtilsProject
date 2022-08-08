package com.io.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner
import java.io.Serializable

abstract class AndroidPresenterController(
    owner: AndroidPresenterStoreOwner
): PresenterController<String>(owner)