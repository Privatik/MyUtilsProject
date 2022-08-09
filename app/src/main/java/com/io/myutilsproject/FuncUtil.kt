package com.io.myutilsproject

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@SuppressLint("ComposableNaming")
@Composable
inline fun <T> use(state: State<T>, content: @Composable (state: T) -> Unit){
    content(state.value)
}