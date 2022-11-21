package com.io.myutilsproject

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.Operation
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.Pop
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.io.myutilsproject.appyx.AppyxPresenterAdapter
import com.io.myutilsproject.appyx.AppyxPresenterStoreOwner
import com.io.myutilsproject.screens.first.FirstNode
import com.io.myutilsproject.screens.second.SecondNode
import com.io.myutilsproject.screens.third.TripleNode
import com.io.navigation.PresenterCompositionLocalProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

sealed class SimpleNode{
    object FirstSimpleNode: SimpleNode()
    object SecondSimpleNode: SimpleNode()
    object ThirdSimpleNode: SimpleNode()
}

class AppyxHost(
    buildContext: BuildContext,
    private val backStack: BackStack<SimpleNode> = BackStack(
        initialElement = SimpleNode.FirstSimpleNode,
        savedStateMap = buildContext.savedStateMap
    )
): ParentNode<SimpleNode>(
    buildContext = buildContext,
    navModel = backStack
){

    override fun resolve(navTarget: SimpleNode, buildContext: BuildContext): Node {
        return when (navTarget){
            SimpleNode.FirstSimpleNode -> FirstNode(
                buildContext = buildContext,
                next = {
                    backStack.push(SimpleNode.SecondSimpleNode)
                }
            )
            SimpleNode.SecondSimpleNode -> SecondNode(
                buildContext,
                open = {
                    backStack.push(SimpleNode.ThirdSimpleNode)
                }
            )
            SimpleNode.ThirdSimpleNode -> TripleNode(
                buildContext,
                backToFirst = {
                    backStack.pop()
                }
            )
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        LaunchedEffect(null){
            backStack.elements
                .onEach {
                    Timber.tag("Navigation").d(it.joinToString("::::"))
                }
                .launchIn(this)
        }

        val owner = remember {
            AppyxPresenterStoreOwner(AppyxPresenterAdapter(backStack))
        }
        PresenterCompositionLocalProvider(
            owner = owner
        ) {
            Children(
                navModel = backStack,
                transitionHandler = rememberBackstackFader(transitionSpec = { tween() })
            )
        }
    }

}