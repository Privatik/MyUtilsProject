package com.io.myutilsproject.screens.first

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.repository.FirstRepository
import com.io.myutilsproject.repository.SecondRepository
import javax.inject.Inject

class FirstPresenter @Inject constructor(
    private val firstRepository: FirstRepository,
    private val secondRepository: SecondRepository,
): Presenter<Any, Any, Any>(Any()) {

    override fun machine(): ReducerDSL<Any, Any>.() -> Unit = {

    }
}
