package com.io.myutilsproject

import android.app.Application
import timber.log.Timber

class App: Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        _appComponent = DaggerAppComponent.builder().build()
        PresenterStore.update(appComponent)
    }
}