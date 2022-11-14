package com.io.navigation

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.navigation_common.PresenterKeyAdapter
import com.io.navigation_common.PresenterStoreOwner
import kotlin.properties.Delegates

interface PresenterComponent<Guide: Any, Key: Any> {
    fun createPresenterOwner(
        activity: ComponentActivity,
        storeOwner: PresenterStoreOwner<Guide, Key>
    )

    val retainPresenterStoreOwner: PresenterStoreOwner<Guide, Key>
}


class DefaultPresenterComponent<Guide: Any, Key: Any>: PresenterComponent<Guide, Key> {

    @Suppress("UNCHECKED_CAST")
    private var vm: ViewModelForPresenter<Guide, Key> by Delegates.notNull()

    override fun createPresenterOwner(
        activity: ComponentActivity,
        storeOwner: PresenterStoreOwner<Guide, Key>
    ) {
        val factory = object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ViewModelForPresenter(storeOwner) as T
            }
        }

        @Suppress("UNCHECKED_CAST")
        vm = ViewModelProvider(activity, factory).get(ViewModelForPresenter::class.java) as ViewModelForPresenter<Guide, Key>
    }



    override val retainPresenterStoreOwner: PresenterStoreOwner<Guide, Key> by lazy(LazyThreadSafetyMode.NONE) { vm.owner }


}