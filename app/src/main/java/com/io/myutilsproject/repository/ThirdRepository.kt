package com.io.myutilsproject.repository

import kotlinx.coroutines.flow.MutableSharedFlow

class ThirdRepository {

    init {
        println("Presenter ThirdRepository")
    }

    val incFlow = MutableSharedFlow<Int>()
    var staterInc: Int = 0

    suspend fun inc(count: Int) {
        incFlow.emit(count)
        staterInc = count
    }
}