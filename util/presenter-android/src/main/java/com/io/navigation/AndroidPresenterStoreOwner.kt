package com.io.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import com.io.navigation.Constants.BACKSTACK_KEYS_FOR_PRESENTER
import com.io.navigation.Constants.RETAIN_PRESENTERS
import com.io.navigation.Constants.RETAIN_PRESENTERS_BY_TAG
import com.io.navigation_common.PresenterStoreOwner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

open class AndroidPresenterStoreOwner: PresenterStoreOwner<String>() {

    fun saveState(): Bundle {
        val retainInfo = restorePresenterStoreOwner.saveInfoAboutShared()
        val retainTag = restorePresenterStoreOwner.saveInfoAboutTag()
        val backList = restorePresenterStoreOwner.saveInfoAboutBackStack()

        val retainPresenters = LinkedList<String>()
        val retainPresentersByTag = LinkedList<String>()

        val bundle = Bundle()
        retainInfo.forEach { (presenter, setScreens) ->
            retainPresenters.add(presenter)
            bundle.putStringArrayList(presenter, ArrayList(setScreens.toList()))
        }
        retainTag.forEach { (tag, key) ->
            retainPresentersByTag.add(tag)
            bundle.putString(tag, key)
        }

        bundle.putStringArrayList(BACKSTACK_KEYS_FOR_PRESENTER, ArrayList(backList))
        bundle.putStringArrayList(RETAIN_PRESENTERS, ArrayList(retainPresenters))
        bundle.putStringArrayList(RETAIN_PRESENTERS_BY_TAG, ArrayList(retainPresentersByTag))

        return bundle
    }

    fun restoreState(bundle: Bundle){
        val backList = bundle.getStringArrayList(BACKSTACK_KEYS_FOR_PRESENTER) ?: emptyList()
        val retainList = bundle.getStringArrayList(RETAIN_PRESENTERS)
        val retainListByTag = bundle.getStringArrayList(RETAIN_PRESENTERS_BY_TAG)

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