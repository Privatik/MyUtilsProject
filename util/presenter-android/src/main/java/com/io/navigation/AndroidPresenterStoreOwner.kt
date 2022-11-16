package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import com.io.navigation.Constants.RETAIN_PRESENTER_LIST
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner

open class AndroidPresenterStoreOwner<Guide: Any>(
    keyAdapter: PresenterKeyAdapter<Guide>
): PresenterStoreOwner<Guide>(keyAdapter) {

    open fun saveState(): Bundle? {
        val sharedPresenters = restorePresenterStoreOwner.saveSharedPresenters()

        val bundle = bundleOf(
            *sharedPresenters
                .map { it.key to it.value }
                .toTypedArray()
        )

        bundle.putStringArray(RETAIN_PRESENTER_LIST, sharedPresenters.keys.toTypedArray())
        return bundle
    }

    open fun restoreState(bundle: Bundle){
        val retain = hashMapOf<String, String>()

        bundle.getStringArray(RETAIN_PRESENTER_LIST)?.forEach { nameClazz ->
            bundle.getString(nameClazz)?.let { cacheKey ->
                @Suppress("UNCHECKED_CAST")
                retain[nameClazz] = cacheKey
            }
        }

        restorePresenterStoreOwner.restoreSharedPresenters(retain)
    }

}