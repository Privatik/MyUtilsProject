package com.io.navigation

import com.io.navigation_common.PresenterStoreOwner
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <Guide: Any> checkOwnerAsAndroidPresenterOwner(owner: PresenterStoreOwner<Guide>){
    contract {
        returns() implies (owner is AndroidPresenterStoreOwner)
    }

    require(owner is AndroidPresenterStoreOwner) {
        "Not correct extends, use AndroidPresenterStoreOwner as parent's owner"
    }
}