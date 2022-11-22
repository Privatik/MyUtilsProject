package com.io.myutilsproject.screens.sixth

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.screens.third.ThirdState
import kotlinx.coroutines.launch

class SixthPresenter: Presenter<ThirdState, Any, Any>(ThirdState()) {

    fun inc(count: Int){
        presenterScope.launch {
            saveHandle.put("inc", count + 1)
        }
    }

    override fun ReducerDSL<ThirdState, Any>.reducer() {
        onEach(
            flow = saveHandle.handleAsFlow<Int>("inc"),
            changeState = { oldState, payload -> oldState.copy(count = payload) }
        )
    }

}