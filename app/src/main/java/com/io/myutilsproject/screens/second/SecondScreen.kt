package com.io.myutilsproject.screens.second

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.io.myutilsproject.use

@Composable
fun SecondScreen(
    body: State<SecondState>,
    inc: (Int) -> Unit,
    incGod: (Int) -> Unit,
    open: () -> Unit
) = use(body){ state ->
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
        Button(onClick = { inc(state.count) }) {
            Text(text = "Inc")
        }
        Button(onClick = { incGod(state.godCount) }) {
            Text(text = "Inc God")
        }
    }
}
