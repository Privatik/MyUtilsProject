package com.io.myutilsproject.screens.eight

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.screens.third.ThirdState

class EighthPresenter: Presenter<ThirdState, Any, Any>(ThirdState() to {}) {

    override fun ReducerDSL<ThirdState, Any>.machine() {

    }
}