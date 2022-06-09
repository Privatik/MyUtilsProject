package com.io.myutilsproject

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import com.io.navigation.AdapterPresenter
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.core.backpress.OnBackPressedDispatcher

fun createNextScope(): NextComponent{
    val component = DaggerNextComponent
        .builder()
        .build()

    return component
}

fun RootController.setupWithActivity(
    activity: ComponentActivity,
    adapter: AdapterPresenter<String>
) {
    setDeepLinkUri(activity.intent?.data?.path)

    val dispatcher = activity.onBackPressedDispatcher
    val rootDispatcher = OnBackPressedDispatcher()
    dispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            rootDispatcher.onBackPressed()
            adapter.pop()
        }
    })

    onApplicationFinish = {
        activity.finish()
    }
    setupBackPressedDispatcher(rootDispatcher)
}