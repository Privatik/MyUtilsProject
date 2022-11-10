package com.io.myutilsproject.appyx

import com.bumble.appyx.core.navigation.NavElements
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.io.myutilsproject.SimpleNode
import com.io.navigation_common.PresenterBackStack
import java.util.*
import kotlin.collections.HashSet

class AppyxKeyBack(

): PresenterBackStack<NavKey<SimpleNode>> {
    private val idsSet = HashSet<NavKey<SimpleNode>>()
    override val backStack: Stack<NavKey<SimpleNode>> = Stack()

    override fun navigateOrPop(key: NavKey<SimpleNode>, removeByKey: (NavKey<SimpleNode>) -> Unit) {
        if (idsSet.contains(key)){
            if (idsSet.isNotEmpty()){
                deleteBackStackUntilKey(key, removeByKey)
            }
        } else {
            backStack.push(key)
            idsSet.add(key)
        }
    }

    private fun deleteBackStackUntilKey(key: NavKey<SimpleNode>, removeByKey: (NavKey<SimpleNode>) -> Unit){
        var screen = backStack.peek()
        while (screen != key){
            idsSet.remove(screen)
            removeByKey(backStack.pop())
            if (backStack.isEmpty()) break
            screen = backStack.peek()
        }
    }

    override fun restoreBackStack(backStack: List<NavKey<SimpleNode>>) {
        backStack.forEach {
            this.backStack.push(it)
            idsSet.add(it)
        }
    }

    override fun saveBackStack(): List<NavKey<SimpleNode>> {
        val list = LinkedList<NavKey<SimpleNode>>()
        backStack.forEach {
            list.add(it)
        }
        return list
    }

}