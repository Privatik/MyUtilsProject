package com.io.navigation

import androidx.lifecycle.SavedStateHandle

class SaveStateStore {
    private val sharedPresenterState = HashMap<String, SavedStateHandle>()
    private val sharedCount = HashMap<String, Int>()

    private val simplePresenterState = HashMap<String, SavedStateHandle>()

    fun getOrCreateNewState(
        key: String,
        isShared: Boolean = false
    ): SavedStateHandle{
        return if (isShared){
            sharedPresenterState.getOrCreateState(key)
        } else {
            simplePresenterState.getOrCreateState(key)
        }
    }

    private fun HashMap<String, SavedStateHandle>.getOrCreateState(key: String): SavedStateHandle{
        return if (containsKey(key)){
            get(key)!!
        } else {
            val state = SavedStateHandle()
            put(key, state)
            state
        }
    }

    fun clearByKey(key: String){
        simplePresenterState.remove(key)
    }

    private data class SharedSaveState(
        val count: Int,
    )

}
