package com.io.myutilsproject.screens.third

import androidx.compose.runtime.Stable
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class ThirdState(
    val count: Int = 0
)

class ThirdPresenter @Inject constructor(): Presenter<ThirdState, Any, Any>(ThirdState()) {


    fun inc(){
//        presenterScope.launch {
//            _count.value = _count.value + 1
//        }
    }

    override val buildMachine: MachineDSL<ThirdState>.() -> Unit = {

    }

}