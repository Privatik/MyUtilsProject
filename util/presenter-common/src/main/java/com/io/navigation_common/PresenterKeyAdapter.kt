package com.io.navigation_common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface PresenterKeyAdapter<Guide: Any> {
   fun getKey(): Guide
}