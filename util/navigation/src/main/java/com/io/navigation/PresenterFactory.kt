package com.io.navigation

interface PresenterFactory{

    fun <P: UIPresenter> create(model: Class<P>): P
}