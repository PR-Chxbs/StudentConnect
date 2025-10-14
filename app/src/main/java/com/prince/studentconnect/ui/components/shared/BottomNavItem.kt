package com.prince.studentconnect.ui.components.shared

import androidx.annotation.DrawableRes
import com.prince.studentconnect.navigation.Screen

data class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
)
