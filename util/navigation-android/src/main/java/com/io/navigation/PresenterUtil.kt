package com.io.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.io.navigation_common.Config

@Composable
fun RebuildConfig(
    rule: Config.() -> Unit
){
    val owner = LocalPresenterOwnerController.current
    rememberSaveable {
        owner.updateConfig(rule)
    }
}