package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import com.io.navigation.Constants.BACKSTACK_KEYS_FOR_PRESENTER
import com.io.navigation.Constants.RETAIN_PRESENTERS
import com.io.navigation.Constants.RETAIN_PRESENTERS_BY_TAG
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

abstract class AndroidPresenterStoreOwner<Guide: Any, CacheKey: Any>(
    private val keyAdapter: PresenterKeyAdapter<Guide>
): PresenterStoreOwner<Guide, CacheKey>(keyAdapter) {

    abstract fun saveState(): Bundle?
    abstract fun restoreState(bundle: Bundle)

}