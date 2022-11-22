package com.io.myutilsproject.screens.second

import androidx.compose.runtime.Stable
import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.repository.FirstRepository
import com.io.myutilsproject.repository.SecondRepository
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
): Presenter<SecondState, Any, SecondEffect>(
    SecondState(godCount = firstRepository.staterInc)
) {

//    private val inc = saveHandle.handleAsFlow<Int>("inc")
    private val incGod = saveHandle.handleAsFlow<Int>("incGod")

    fun inc(count: Int){
        presenterScope.launch {
            saveHandle.put("inc", count + 1)
        }
    }

    fun incGod(count: Int){
        presenterScope.launch {
            saveHandle.put("incGod", count + 1)
        }
    }

    override fun ReducerDSL<SecondState, SecondEffect>.reducer() {
        onEach(
            flow = saveHandle.handleAsFlow<Int>("inc"),
            changeState = { oldState, payload ->
                oldState.copy(count = payload)
            },
            effect = { _, _, payload ->
                println("Machine $payload")
                SecondEffect.Snack(payload.toString())
            }
        )

        onEach(
            flow = incGod,
            action = { _, _, payload ->
                firstRepository.inc(payload)
            }
        )

        onEach(
            flow = firstRepository.incFlow,
            changeState = { oldState, payload ->
                oldState.copy(godCount = payload)
            }
        )
    }

}