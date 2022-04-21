package com.io.navigation

interface PresenterFactory<Presenter: com.io.navigation.Presenter>{

    fun <P: Presenter> create(model: Class<P>): Presenter
}