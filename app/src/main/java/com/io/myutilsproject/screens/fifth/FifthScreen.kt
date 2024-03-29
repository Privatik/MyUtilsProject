package com.io.myutilsproject.screens.fifth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.io.myutilsproject.screens.third.ThirdState

@Composable
fun FifthScreen(
    state: ThirdState,
    inc: (Int) -> Unit,
    open: () -> Unit
){
    Column() {
        Text("FifthScreen")
        Text("state ${state.count}")
        Button(onClick = { inc(state.count) }) {

        }
        Button(onClick = { open() }) {
            Text("Open")
        }
    }
}