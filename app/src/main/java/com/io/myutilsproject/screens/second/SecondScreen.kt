package com.io.myutilsproject.screens.second

import android.content.res.Configuration
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
import com.io.myutilsproject.SharedAppComponentPresenter
import com.io.myutilsproject.screens.fifth.FifthPresenter
import com.io.myutilsproject.screens.third.ThirdState
import com.io.myutilsproject.use
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter

@Composable
fun SecondScreen(
    body: State<SecondState>,
    bodyT: State<ThirdState>,
    inc: (Int) -> Unit,
    incGod: (Int) -> Unit,
    incTag: (Int) -> Unit,
    open: () -> Unit
) = use(body){ state ->
    val currentConfig = LocalConfiguration.current
    val stateT = bodyT.value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Text(text = "Second screen LANDSCAPE ${state.count} and ${stateT.count}")
            Text(text = "Second screen GOD LANDSCAPE ${state.godCount} and ${stateT.count}")
        } else {
            Text(text = "Second screen PORTSCAPE ${state.count} and ${stateT.count}")
            Text(text = "Second screen GOD PORTSCAPE ${state.godCount} and ${stateT.count}")
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
        Button(onClick = { incTag(stateT.count) }) {
            Text(text = "Inc by tag God")
        }
    }
}

class SecondNode(
    buildContext: BuildContext,
    private val open: () -> Unit
): Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val appScopePresenter: SharedAppComponentPresenter = sharedPresenter()
        val secondPresenter: SecondPresenter = presenter(appScopePresenter.factory)
        val fifthPresenter: FifthPresenter = sharedPresenter()

        Content(
            body = secondPresenter.state.collectAsState(),
            bodyT = fifthPresenter.state.collectAsState(),
            inc = { secondPresenter.inc(it + 1) },
            incGod = { fifthPresenter.inc(it + 1) },
            incTag = { }
        )
    }

    @Composable
    private fun Content(
        body: State<SecondState>,
        bodyT: State<ThirdState>,
        inc: (Int) -> Unit,
        incGod: (Int) -> Unit,
        incTag: (Int) -> Unit,
    ) = use(body){ state ->
        val currentConfig = LocalConfiguration.current
        val stateT = bodyT.value

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                Text(text = "Second screen LANDSCAPE ${state.count} and ${stateT.count}")
                Text(text = "Second screen GOD LANDSCAPE ${state.godCount} and ${stateT.count}")
            } else {
                Text(text = "Second screen PORTSCAPE ${state.count} and ${stateT.count}")
                Text(text = "Second screen GOD PORTSCAPE ${state.godCount} and ${stateT.count}")
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
            Button(onClick = { incTag(stateT.count) }) {
                Text(text = "Inc by tag God")
            }
        }
    }
}
