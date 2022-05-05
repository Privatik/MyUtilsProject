package com.io.myutilsproject

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SecondScreen(
    state: Int,
    inc: () -> Unit,
    open: () -> Unit
){
    val currentConfig = LocalConfiguration.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { inc() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE $state")
        } else {
            Text(text = "Second screen PORTSCAPE $state")
        }
        Button(onClick = { open() }) {
            Text(text = "Open")
        }

    }
}
