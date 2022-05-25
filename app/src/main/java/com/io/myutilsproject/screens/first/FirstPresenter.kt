package com.io.myutilsproject.screens.first

import com.example.machine.MachineDSL
import com.io.myutilsproject.Presenter
import javax.inject.Inject

class FirstPresenter @Inject constructor(): Presenter<Any, Any, Any>(Any()) {

    override fun buildMachine(): MachineDSL<Any, Any>.() -> Unit = {

    }
}
