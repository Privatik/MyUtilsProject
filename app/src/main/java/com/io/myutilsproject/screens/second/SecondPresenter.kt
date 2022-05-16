package com.io.myutilsproject.screens.second

import com.io.myutilsproject.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecondPresenter @Inject constructor(): Presenter() {

    private var _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun inc(){
        presenterScope.launch {
            _count.value = _count.value + 1
        }
    }

}