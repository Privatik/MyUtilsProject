package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import com.io.navigation.Constants.BACKSTACK_KEYS_FOR_PRESENTER
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter

open class AndroidPresenterStoreOwner: PresenterStoreOwner<String>() {

    fun saveState(): Bundle {
        val backArray = Array(backStack.size) { "" }
        backStack.forEachIndexed { index, s ->
            backArray[index] = s
        }

        return bundleOf(BACKSTACK_KEYS_FOR_PRESENTER to backArray)
    }

    fun restoreState(bundle: Bundle){
        val backArray = bundle.getStringArray(BACKSTACK_KEYS_FOR_PRESENTER)

        checkNotNull(backArray){
            "backStack not parse"
        }

        backArray.forEach {
            backStack.push(it)
        }
    }
}