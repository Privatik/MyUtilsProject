package com.io.myutilsproject.appyx

import android.os.Bundle
import com.bumble.appyx.core.navigation.NavElements
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.io.myutilsproject.SimpleNode
import com.io.navigation.Constants
import com.io.navigation_common.PresenterStoreOwner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class AppyxAndroidPresenterStoreOwner: PresenterStoreOwner<NavKey<SimpleNode>>(
    keyBackStack = AppyxKeyBack()
) {

    fun saveState(): Bundle {
        val retainInfo = restorePresenterStoreOwner.saveInfoAboutShared()
        val retainTag = restorePresenterStoreOwner.saveInfoAboutTag()
        val backList = restorePresenterStoreOwner.saveInfoAboutBackStack()

        val retainPresenters = LinkedList<String>()
        val retainPresentersByTag = LinkedList<String>()

        val bundle = Bundle()
        retainInfo.forEach { (presenter, setScreens) ->
            retainPresenters.add(presenter)
            bundle.putParcelableArrayList(presenter, ArrayList(setScreens.toList()))
        }
        retainTag.forEach { (tag, key) ->
            retainPresentersByTag.add(tag)
            bundle.putParcelable(tag, key)
        }

        bundle.putParcelableArray(Constants.BACKSTACK_KEYS_FOR_PRESENTER, backList.toTypedArray())
        bundle.putStringArrayList(Constants.RETAIN_PRESENTERS, ArrayList(retainPresenters))
        bundle.putStringArrayList(Constants.RETAIN_PRESENTERS_BY_TAG, ArrayList(retainPresentersByTag))

        return bundle
    }

    fun restoreState(bundle: Bundle){
        val backList = bundle.getParcelableArray(Constants.BACKSTACK_KEYS_FOR_PRESENTER, NavKey::class.java) ?: emptyList()
        val retainList = bundle.getStringArrayList(Constants.RETAIN_PRESENTERS)
        val retainListByTag = bundle.getStringArrayList(Constants.RETAIN_PRESENTERS_BY_TAG)

        val retainKeys = HashMap<String, HashSet<String>>()
        val retainKeysByTag = HashMap<String, String>()

        retainList?.forEach {
            bundle.getStringArrayList(it)?.toSet()?.let { set ->
                retainKeys[it] = HashSet(set)
            }
        }

        retainListByTag?.forEach {
            bundle.getString(it)?.let { key ->
                retainKeysByTag[it] = key
            }
        }

        restorePresenterStoreOwner.restoreInfoAboutBackStack(backList.toList())
        restorePresenterStoreOwner.restoreInfoAboutShared(retainKeys)
        restorePresenterStoreOwner.restoreInfoAboutTag(retainKeysByTag)
    }
}