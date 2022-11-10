package com.io.navigation

import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner

abstract class AndroidPresenterKeyAdapter<Key: Any>(
    owner: PresenterStoreOwner<Key>
): PresenterKeyAdapter<Key>(owner)