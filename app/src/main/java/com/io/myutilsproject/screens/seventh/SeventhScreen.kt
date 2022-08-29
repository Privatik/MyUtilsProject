package com.io.myutilsproject.screens.seventh

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.io.myutilsproject.screens.third.ThirdState

@Composable
fun SeventhScreen(
    state: ThirdState,
    inc: (Int) -> Unit,
){
    Column() {
        Text("SevenScreen")
        Text("state ${state.count}")
        Button(onClick = { inc(state.count + 1) }) {
            
        }
    }
    
}