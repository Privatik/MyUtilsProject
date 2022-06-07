package com.io.myutilsproject.screens.sixth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.io.myutilsproject.screens.third.ThirdState

@Composable
fun SixthScreen(
    state: ThirdState,
    inc: () -> Unit,
    open: () -> Unit
){
    Column() {
        Text("SixthScreen")
        Text("state ${state.count}")
        Button(onClick = { inc() }) {
            
        }
        Button(onClick = { open() }) {
            Text("open")
        }
    }
    
}