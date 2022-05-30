package com.io.myutilsproject.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirstRepository{

    init {
        println("Presenter FirstRepository")
    }

    val incFlow = MutableSharedFlow<Int>()
    var staterInc: Int = 0

    suspend fun inc(count: Int) {
        incFlow.emit(count)
        staterInc = count
    }
}