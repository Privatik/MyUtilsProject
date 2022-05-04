package com.io.myutilsproject

import kotlin.properties.Delegates

object PresenterStore {

    var presenterDeps: PresenterDeps by Delegates.notNull()
        private set

    fun update(presenterDeps: PresenterDeps){
        this.presenterDeps = presenterDeps
    }
}