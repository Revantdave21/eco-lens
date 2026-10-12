package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val OrangeWhiteColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = PureWhite,
    primaryContainer = OrangeContainerLight,
    onPrimaryContainer = OrangePrimaryDark,
    secondary = OrangeSecondary,
    onSecondary = PureWhite,
    secondaryContainer = OrangeContainerMuted,
    onSecondaryContainer = OrangePrimaryDark,
    background = CleanCanvas,
    surface = CardSurfaceWhite,
    surfaceVariant = OrangeTint,
    onBackground = TextCharcoal,
    onSurface = TextCharcoal,
    outline = BorderSubtle
)

@Composable
fun EcoLensTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = OrangeWhiteColorScheme,
        typography = EcoLensTypography,
        content = content
    )
}
