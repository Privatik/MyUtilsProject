package com.io.myutilsproject.screens.fifth

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.screens.third.ThirdState
import kotlinx.coroutines.launch

class FifthPresenter: Presenter<ThirdState, Any, Any>(ThirdState() to {}) {

    fun inc(count: Int){
        presenterScope.launch {
            saveHandle.put("inc", count + 1)
        }
    }

    override fun ReducerDSL<ThirdState, Any>.machine() {
        onEach(
            everyFlow = saveHandle.handleAsFlow<Int>("inc"),
            updateState = { oldState, payload -> oldState.copy(count = payload) }
        )
    }
}