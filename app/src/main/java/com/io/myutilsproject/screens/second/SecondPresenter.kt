package com.io.myutilsproject.screens.second

import com.example.machine.Machine
import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondPresenter @Inject constructor(): Presenter<Any,Any,Any>() {

    private var _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun inc(){
//        presenterScope.launch {
//            _count.value = _count.value + 1
//        }
    }

    val buildMachine: MachineDSL<Any>.() -> Unit = {
        onEach(flowOf(1,2,3)){

        }
    }
    override val MachineDSL<Any>.buildMachine: Machine<Any>.() -> Unit = {
        onEach(flowOf(1,2,3)){

        }
    }

}