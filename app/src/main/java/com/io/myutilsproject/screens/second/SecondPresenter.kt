package com.io.myutilsproject.screens.second

import androidx.compose.runtime.Stable
import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.repository.FirstRepository
import com.io.myutilsproject.repository.SecondRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class SecondState(
    val count: Int = 0,
    val godCount: Int
)

sealed class SecondEffect{
    data class Snack(val message: String): SecondEffect()
}

class SecondPresenter @Inject constructor(
    private val firstRepository: FirstRepository,
    private val secondRepository: SecondRepository,
): Presenter<SecondState, Any, SecondEffect>(SecondState(godCount = firstRepository.staterInc)) {

    private val inc = MutableSharedFlow<Int>()
    private val incGod = MutableSharedFlow<Int>()

    fun inc(count: Int){
        presenterScope.launch {
            inc.emit(count + 1)
        }
    }

    fun incGod(count: Int){
        presenterScope.launch {
            incGod.emit(count + 1)
        }
    }

    override fun machine(): ReducerDSL<SecondState, SecondEffect>.() -> Unit = {
        onEach(
            everyFlow = inc,
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

        onEach(
            everyFlow = incGod,
            action = { _, _, payload ->
                firstRepository.inc(payload)
            }
        )

        onEach(
            everyFlow = firstRepository.incFlow,
            updateState = { oldState, payload ->
                oldState.copy(godCount = payload)
            }
        )
    }

}