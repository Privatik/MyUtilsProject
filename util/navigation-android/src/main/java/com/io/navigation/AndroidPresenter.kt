package com.io.navigation

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.io.navigation_common.UIPresenter
import kotlinx.coroutines.flow.Flow

abstract class AndroidPresenter: UIPresenter {
    protected val savedBundle = Bundle()

    internal fun restore(bundle: Bundle){
        savedBundle.putAll(bundle)
    }

    internal fun save(): Bundle{
        return Bundle(savedBundle)
    }

}