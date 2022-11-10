package com.io.myutilsproject

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
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
    adapter: com.io.navigation_common.PresenterKeyAdapter<String>
) {
    setDeepLinkUri(activity.intent?.data?.path)

    val dispatcher = activity.onBackPressedDispatcher
    val rootDispatcher = OnBackPressedDispatcher()
    dispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            rootDispatcher.onBackPressed()
        }
    })

    onApplicationFinish = {
        activity.finish()
    }
    setupBackPressedDispatcher(rootDispatcher)
}