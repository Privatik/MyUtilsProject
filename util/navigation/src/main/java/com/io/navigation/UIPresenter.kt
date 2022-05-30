package com.io.navigation

import kotlinx.coroutines.flow.StateFlow

interface UIPresenter{
    fun build()
    fun clear()
}