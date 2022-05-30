package com.io.myutilsproject.screens.third

import androidx.compose.runtime.Stable
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class ThirdState(
    val count: Int = 0
)

class ThirdPresenter @Inject constructor(): Presenter<ThirdState, Any, Any>(ThirdState()) {

    private val inc = MutableSharedFlow<Int>()

    fun inc(count: Int){
        presenterScope.launch {
            inc.emit(count + 1)
        }
    }

    override fun machine(): MachineDSL<ThirdState, Any>.() -> Unit = {
        onEach(
            everyFlow = inc,
            updateState = { oldState, payload -> oldState.copy(count = payload) }
        )
    }


}