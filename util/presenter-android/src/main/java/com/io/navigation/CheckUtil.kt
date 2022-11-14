package com.io.navigation

import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <Guide: Any, Key: Any> checkOwnerAsAndroidPresenterOwner(owner: PresenterStoreOwner<Guide,Key>){
    contract {
        returns() implies (owner is AndroidPresenterStoreOwner)
    }

    require(owner is AndroidPresenterStoreOwner) {
        "Not correct extends, use AndroidPresenterStoreOwner as parent's owner"
    }
}