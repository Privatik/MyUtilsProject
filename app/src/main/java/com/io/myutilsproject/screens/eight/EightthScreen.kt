package com.io.myutilsproject.screens.eight

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.io.myutilsproject.screens.third.ThirdState

@Composable
fun EighthScreen(
    state: ThirdState,
    inc: (Int) -> Unit
) {
    Column() {
        Text("EighthScreen")
        Text("state ${state.count}")
        Button(onClick = { inc(state.count + 1) }) {

        }
    }
}