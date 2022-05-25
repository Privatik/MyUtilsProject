package com.io.myutilsproject.screens.second

import androidx.compose.runtime.Stable
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableSharedFlow
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

    override fun buildMachine(): MachineDSL<SecondState, SecondEffect>.() -> Unit = {
        onEach(
            everyFlow = incFlow,
            updateState = { oldState, payload ->
                oldState.copy(count = payload)
                          },
            effect = { _, _, payload ->
                println("Machine $payload")
                if (payload % 10 == 0 && payload != 0){
                    SecondEffect.Snack("Ten")
                } else {
                    null
                }
            }
        )
    }

}