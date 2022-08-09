package com.io.navigation

import com.io.navigation_common.PresenterStoreOwner
import com.io.navigation_common.UIPresenter
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun checkOwnerAsAndroidPresenterOwner(owner: PresenterStoreOwner<out Any>){
    contract {
        returns() implies (owner is AndroidPresenterStoreOwner)
    }

    require(owner is AndroidPresenterStoreOwner) {
        "Not correct extends, use AndroidPresenterStoreOwner as parent for owner"
    }
}