package com.io.myutilsproject.screens.seventh

import com.example.machine.ReducerDSL
import com.io.myutilsproject.Presenter
import com.io.myutilsproject.screens.third.ThirdState

class SeventhPresenter: Presenter<ThirdState, Any, Any>(ThirdState() to {}) {

    override fun ReducerDSL<ThirdState, Any>.machine() {

    }
}