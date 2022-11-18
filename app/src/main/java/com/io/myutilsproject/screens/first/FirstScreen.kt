package com.io.myutilsproject.screens.first

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.BuildConfig
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.io.myutilsproject.SharedAppComponentPresenter
import com.io.navigation.sharedPresenter

@Composable
fun FirstScreen(
    next: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { next() }) {
            Text(text = "Next")
        }
    }
}

class FirstNode(
    buildContext: BuildContext,
    private val next: () -> Unit
): Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
       Content()
    }

    @Composable
    private fun Content(){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = next) {
                Text(text = "Next")
            }
        }
    }
}