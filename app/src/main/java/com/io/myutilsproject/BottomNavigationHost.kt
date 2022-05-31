package com.io.myutilsproject

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.io.myutilsproject.ui.theme.Purple200
import com.io.myutilsproject.ui.theme.Purple700
import com.io.myutilsproject.ui.theme.Teal200
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel

class BottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = Purple700,
                selectedColor = Teal200,
                unselectedColor = Purple200
            )
        }
}

class FifthTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = "Fifth"
            )
        }
}

class SixthTab : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = "Sixth"
            )
        }
}