package com.io.myutilsproject.screens.first

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.repository.SecondRepository
import javax.inject.Inject

class FirstPresenter @Inject constructor(
    private val secondRepository: SecondRepository,
): Presenter<Any, Any, Any>(Pair(Any()) {}) {

    override fun ReducerDSL<Any, Any>.reducer() {

    }

}
