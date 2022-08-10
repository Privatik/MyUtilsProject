package com.io.navigation

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.io.navigation_common.UIPresenter
import kotlinx.coroutines.flow.Flow

abstract class AndroidPresenter: UIPresenter {
    private var _savedBundle: Bundle? = null
    protected val savedBundle
        get() = run {
            if (_savedBundle == null){
                _savedBundle = Bundle()
            }
            _savedBundle!!
        }


    internal fun restore(bundle: Bundle){
        _savedBundle = bundle
    }

    internal fun save(): Bundle?{
        return _savedBundle
    }

}