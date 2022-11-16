package com.io.myutilsproject

import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.io.myutilsproject.screens.first.FirstNode
import com.io.myutilsproject.screens.second.SecondNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

sealed class SimpleNode{
    object FirstSimpleNode: SimpleNode()
    data class SecondSimpleNode(val count: Int): SimpleNode()
}

class AppyxHost(
    buildContext: BuildContext,
    scope: CoroutineScope,
    private val backStack: BackStack<SimpleNode> = BackStack(
        initialElement = SimpleNode.FirstSimpleNode,
        savedStateMap = buildContext.savedStateMap
    )
): ParentNode<SimpleNode>(
    buildContext = buildContext,
    navModel = backStack
){

    init {
        backStack.elements
            .onEach {
                Timber.tag("Navigation").d(it.joinToString("::::"))
            }
            .launchIn(scope)
    }

    override fun resolve(navTarget: SimpleNode, buildContext: BuildContext): Node {
        return when (navTarget){
            SimpleNode.FirstSimpleNode -> FirstNode(
                buildContext = buildContext,
                next = {
                    backStack.pop()
                    backStack.push(SimpleNode.SecondSimpleNode(10))
                }
            )
            is SimpleNode.SecondSimpleNode -> SecondNode(buildContext, navTarget.count)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackFader(transitionSpec = { tween() })
        )
    }

}