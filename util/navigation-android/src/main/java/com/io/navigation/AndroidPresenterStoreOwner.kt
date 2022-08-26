package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import com.io.navigation.Constants.BACKSTACK_KEYS_FOR_PRESENTER
import com.io.navigation.Constants.RETAIN_PRESENTERS
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

open class AndroidPresenterStoreOwner: PresenterStoreOwner<String>() {

    fun saveState(): Bundle {
        val retainInfo = saveInfoAboutShared()

        val backArray = Array(backStack.size) { "" }
        val retainPresenters = LinkedList<String>()
        backStack.forEachIndexed { index, s ->
            backArray[index] = s
        }

        val bundle = bundleOf(BACKSTACK_KEYS_FOR_PRESENTER to backArray)
        retainInfo.forEach { (presenter, setScreens) ->
            retainPresenters.add(presenter)
            bundle.putStringArrayList(presenter, ArrayList(setScreens.toList()))
        }
        bundle.putStringArrayList(RETAIN_PRESENTERS, ArrayList(retainPresenters))

        return bundle
    }

    fun restoreState(bundle: Bundle){
        val backArray = bundle.getStringArray(BACKSTACK_KEYS_FOR_PRESENTER)
        val retainList = bundle.getStringArrayList(RETAIN_PRESENTERS)

        checkNotNull(backArray){
            "backStack not parse"
        }

        val retainKeys = HashMap<String, HashSet<String>>()

        backArray.forEach {
            backStack.push(it)
        }

        retainList?.forEach {
            bundle.getStringArrayList(it)?.toSet()?.let { set ->
                retainKeys[it] = HashSet(set)
            }
        }

        restoreInfoAboutShared(retainKeys)
    }
}