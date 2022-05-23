package com.io.myutilsproject.screens.third

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun TripleScreen(
    state: ThirdState,
    inc: () -> Unit,
    backToFirst: () -> Unit
){
    val currentConfig = LocalConfiguration.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { inc() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE ${state.count}")
        } else {
            Text(text = "Second screen PORTSCAPE ${state.count}")
        }
        Button(onClick = { backToFirst() }) {

        }
    }
}
