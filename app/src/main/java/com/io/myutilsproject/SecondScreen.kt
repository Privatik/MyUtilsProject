package com.io.myutilsproject

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun SecondScreen(){
    val currentConfig = LocalConfiguration.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen")
        } else {
            Text(text = "First screen")
        }

    }
}