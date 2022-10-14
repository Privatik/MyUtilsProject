package com.io.navigation_common

import java.util.*
import kotlin.collections.HashSet

interface PresenterBackStack<Key: Any>{
    val backStack: Stack<Key>
    fun navigateOrPop(key: Key, removeByKey:(Key) -> Unit)

    fun restoreBackStack(backStack: List<Key>)
    fun saveBackStack(): List<Key>

}

internal class KeyBackStack<Key: Any>: PresenterBackStack<Key> {
    private val keySets = HashSet<Key>()
    override val backStack = Stack<Key>()

    override fun navigateOrPop(key: Key, removeByKey: (Key) -> Unit) {
        if (keySets.contains(key)){
            if (keySets.isNotEmpty()){
                deleteBackStackUntilKey(key, removeByKey)
            }
        } else {
            backStack.push(key)
            keySets.add(key)
        }
    }

    private fun deleteBackStackUntilKey(key: Key, removeByKey: (Key) -> Unit){
        var screen = backStack.peek()
        while (screen != key){
            keySets.remove(screen)
            removeByKey(backStack.pop())
            if (backStack.isEmpty()) break
            screen = backStack.peek()
        }
    }

    override fun restoreBackStack(backStack: List<Key>) {
        backStack.forEach {
            this.backStack.push(it)
            keySets.add(it)
        }
    }

    override fun saveBackStack(): List<Key> {
        val list= LinkedList<Key>()
        backStack.forEach {
            list.add(it)
        }
        return list
    }


}