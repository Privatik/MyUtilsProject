package com.io.myutilsproject.screens.third

import androidx.compose.runtime.Stable
import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class ThirdState(
    val count: Int = 0
)

class ThirdPresenter @Inject constructor(): Presenter<ThirdState, Any, Any>(Pair(ThirdState()) {}) {

    private val inc = saveHandle.handleAsFlow<Int>("inc")

    fun inc(count: Int){
        presenterScope.launch {
            saveHandle.put("inc", count + 1)
        }
    }

    override fun ReducerDSL<ThirdState, Any>.machine() {
        onEach(
            everyFlow = inc,
            updateState = { oldState, payload -> oldState.copy(count = payload) }
        )
    }


}