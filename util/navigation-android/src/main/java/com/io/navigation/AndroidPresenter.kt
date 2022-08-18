package com.io.navigation

import android.os.Bundle
import com.io.navigation_common.UIPresenter

abstract class AndroidPresenter: UIPresenter {
    private var _saveHandle: SaveHandle? = null
    protected val saveHandle
        get() = run {
            if (_saveHandle == null){
                _saveHandle = SaveHandle(Bundle())
            }
            _saveHandle!!
        }


    internal fun restore(bundle: Bundle){
        saveHandle.update(bundle)
    }

    internal fun save(): Bundle?{
        return _saveHandle?.getBundle()
    }

}