package com.io.navigation

import androidx.lifecycle.ViewModel
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner

class ViewModelForPresenter<Guide: Any, CacheKey: Any>(
    val owner : PresenterStoreOwner<Guide, CacheKey>
): ViewModel()