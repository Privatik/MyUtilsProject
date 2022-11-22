package com.io.myutilsproject.screens.third

import androidx.compose.runtime.Stable
import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class ThirdState(
    val count: Int = 0
)

class ThirdPresenter @Inject constructor(): Presenter<ThirdState, Any, Any>(ThirdState()) {

    private val inc = saveHandle.handleAsFlow<Int>("inc")

    fun inc(count: Int){
        presenterScope.launch {
            saveHandle.put("inc", count + 1)
        }
    }

    override fun ReducerDSL<ThirdState, Any>.reducer() {
        onEach(
            flow = inc,
            changeState = { oldState, payload -> oldState.copy(count = payload) }
        )
    }


}