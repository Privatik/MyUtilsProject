package com.io.myutilsproject

import com.io.navigation.AndroidPresenter
import com.io.navigation_common.PresenterFactory
import kotlin.properties.Delegates

class SharedAppComponentPresenter(): AndroidPresenter(){

    @Volatile
    private var _factory: PresenterFactory? = null

    val factory: PresenterFactory?
        get() = _factory

    init {
        _factory = DaggerAppComponent.builder().build().factory
    }

    override fun clear() {
        _factory = null
    }


}

class SharedNextComponentPresenter(): AndroidPresenter(){

    @Volatile
    private var _factory: PresenterFactory? = null

    val factory: PresenterFactory?
        get() = _factory

    init {
        _factory = DaggerNextComponent.builder().build().factory
    }

    override fun clear() {
        _factory = null
    }


}