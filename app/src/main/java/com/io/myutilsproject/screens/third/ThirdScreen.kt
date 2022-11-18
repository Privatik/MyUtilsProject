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
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.io.myutilsproject.Screens
import com.io.myutilsproject.SharedAppComponentPresenter
import com.io.myutilsproject.SharedNextComponentPresenter
import com.io.myutilsproject.screens.fifth.FifthPresenter
import com.io.myutilsproject.screens.second.SecondPresenter
import com.io.myutilsproject.screens.second.SecondState
import com.io.myutilsproject.use
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter

@Composable
fun TripleScreen(
    state: State<ThirdState>,
    stateT: State<ThirdState>,
    inc: (Int) -> Unit,
    incT: (Int) -> Unit,
    backToFirst: () -> Unit,
    next: () -> Unit
){
    val currentConfig = LocalConfiguration.current
    val value = state.value
    val valueT = stateT.value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE ${value.count} and ${valueT.count}")
        } else {
            Text(text = "Second screen PORTSCAPE ${value.count} and ${valueT.count}")
        }
        Button(onClick = { backToFirst() }) {
            Text(text = "Back")
        }
        Button(onClick = { next() }) {
            Text(text = "Next")
        }
        Button(onClick = { inc(value.count) }) {
            Text(text = "click")
        }
        Button(onClick = { incT(valueT.count) }) {
            Text(text = "click on tag")
        }
    }
}

class TripleNode(
    buildContext: BuildContext,
    private val backToFirst: () -> Unit
): Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val nextScopePresenter: SharedNextComponentPresenter = sharedPresenter()
        val thirdPresenter: ThirdPresenter = presenter(nextScopePresenter.factory)
        val fifthPresenter: FifthPresenter = sharedPresenter()

        Content(
            state = thirdPresenter.state.collectAsState(),
            stateT = fifthPresenter.state.collectAsState(),
            inc = { fifthPresenter.inc(it + 1) },
            next = {

            },
            incT = {

            }
        )
    }

    @Composable
    private fun Content(
        state: State<ThirdState>,
        stateT: State<ThirdState>,
        inc: (Int) -> Unit,
        incT: (Int) -> Unit,
        next: () -> Unit
    ) {
        val currentConfig = LocalConfiguration.current
        val value = state.value
        val valueT = stateT.value

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                Text(text = "Second screen LANDSCAPE ${value.count} and ${valueT.count}")
            } else {
                Text(text = "Second screen PORTSCAPE ${value.count} and ${valueT.count}")
            }
            Button(onClick = { backToFirst() }) {
                Text(text = "Back")
            }
            Button(onClick = { next() }) {
                Text(text = "Next")
            }
            Button(onClick = { inc(value.count) }) {
                Text(text = "click")
            }
            Button(onClick = { incT(valueT.count) }) {
                Text(text = "click on tag")
            }
        }
    }
}

