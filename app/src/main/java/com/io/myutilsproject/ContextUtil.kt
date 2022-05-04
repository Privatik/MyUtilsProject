package com.io.myutilsproject

import android.content.Context

fun Context.appComponent(): AppComponent = when(this){
    is App -> appComponent
    else -> (applicationContext as App).appComponent
}
