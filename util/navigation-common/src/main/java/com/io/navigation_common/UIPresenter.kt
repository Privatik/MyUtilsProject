package com.io.navigation_common

import kotlinx.coroutines.flow.StateFlow

interface UIPresenter{
    fun build() {}
    fun clear()
}