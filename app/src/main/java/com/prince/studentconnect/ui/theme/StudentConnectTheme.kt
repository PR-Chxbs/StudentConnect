package com.prince.studentconnect.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.prince.studentconnect.R


@Composable
fun StudentConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val LightColors = lightColorScheme(
        primary = colorResource(id = R.color.green_main),
        onPrimary = colorResource(id = R.color.black),
        primaryContainer = colorResource(id = R.color.grey_white),
        surface = colorResource(id = R.color.grey_white),
        secondary = colorResource(id = R.color.green_secondary),
        secondaryContainer = colorResource(id = R.color.white),
        background = colorResource(id = R.color.grey_white),
        onBackground = colorResource(id = R.color.black),
        outline = colorResource(id = R.color.outline_grey),
        surfaceVariant = colorResource(id = R.color.white)
    )

    val DarkColors = darkColorScheme(
        primary = colorResource(id = R.color.green_main),
        onPrimary = colorResource(id = R.color.black),
        primaryContainer = colorResource(id = R.color.blue_black),
        surface = colorResource(id = R.color.blue_black),
        secondary = colorResource(id = R.color.green_secondary),
        background = colorResource(id = R.color.blue_black),
        onBackground = colorResource(id = R.color.white),
        outline = colorResource(id = R.color.dark_outline_grey),
        surfaceVariant = colorResource(id = R.color.variant_blue_black)
    )

    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
