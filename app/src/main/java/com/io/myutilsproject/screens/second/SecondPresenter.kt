package com.io.myutilsproject.screens.second

import androidx.compose.runtime.Stable
import com.example.machine.Machine
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class SecondState(
    val count: Int = 0
)

class SecondPresenter @Inject constructor(): Presenter<SecondState,Any,Any>(SecondState()) {

    fun inc(){
//        presenterScope.launch {
//            _count.value = _count.value + 1
//        }
    }

    override val buildMachine: (MachineDSL<SecondState>.() -> Unit) = {
        startState = SecondState()
        startAction = {

        }

        startAction = {

        }

        onEach(flowOf<Int>(1,2,3)){
            state { oldState, payload ->
                oldState.copy(count = payload)
            }

            action { oldState, newState, payload ->

            }
        }
    }

}