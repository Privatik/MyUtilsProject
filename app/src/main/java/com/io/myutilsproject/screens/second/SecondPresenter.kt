package com.io.myutilsproject.screens.second

import androidx.compose.runtime.Stable
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@Stable
data class SecondState(
    val count: Int = 0
)

sealed class SecondEffect{
    data class Snack(val message: String): SecondEffect()
}

class SecondPresenter @Inject constructor(): Presenter<SecondState, Any , SecondEffect>(SecondState()) {

    fun inc(){
//        presenterScope.launch {
//            _count.value = _count.value + 1
//        }
    }

    override val buildMachine: MachineDSL<SecondState, SecondEffect>.() -> Unit = {
        onEach(flowOf(1,2,3)){
            state { oldState, payload ->
                oldState.copy(count = payload)
            }

            action { oldState, newState, payload ->

            }
        }

        onEach(flowOf(1,2,3)){
            state { oldState, payload ->
                oldState.copy(count = payload)
            }

//            effect { oldState, newState, payload ->
//                SecondEffect.Snack("Hello")
//            }
        }
    }

}