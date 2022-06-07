package com.io.myutilsproject.screens.second

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SecondScreen(
    state: SecondState,
    inc: () -> Unit,
    incGod: () -> Unit,
    open: () -> Unit
){
    val currentConfig = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE ${state.count}")
            Text(text = "Second screen GOD LANDSCAPE ${state.godCount}")
        } else {
            Text(text = "Second screen PORTSCAPE ${state.count}")
            Text(text = "Second screen GOD PORTSCAPE ${state.godCount}")
        }
        Button(onClick = { open() }) {
            Text(text = "Open")
        }
        Button(onClick = { inc() }) {
            Text(text = "Inc")
        }
        Button(onClick = { incGod() }) {
            Text(text = "Inc God")
        }
    }
}
