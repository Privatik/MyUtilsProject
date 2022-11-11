package com.io.navigation_common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


abstract class PresenterKeyAdapter<Key: Any>(

){
    abstract fun getKey(): Key
}