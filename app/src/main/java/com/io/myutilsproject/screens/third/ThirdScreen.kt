package com.io.myutilsproject.screens.third

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun TripleScreen(
    state: State<ThirdState>,
    inc: (Int) -> Unit,
    backToFirst: () -> Unit,
    next: () -> Unit
){
    val currentConfig = LocalConfiguration.current
    val value = state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { inc(value.count) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE ${value.count}")
        } else {
            Text(text = "Second screen PORTSCAPE ${value.count}")
        }
        Button(onClick = { backToFirst() }) {
            Text(text = "Back")
        }
        Button(onClick = { next() }) {
            Text(text = "Next")
        }
    }
}
