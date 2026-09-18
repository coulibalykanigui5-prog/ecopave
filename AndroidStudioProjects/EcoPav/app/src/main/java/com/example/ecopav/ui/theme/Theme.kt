package com.example.ecopav.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EcoGreen,
    secondary = Terracotta,
    tertiary = Slate,
    background = Slate,
    surface = Slate,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = SandSoft,
    onSurface = SandSoft
)

private val LightColorScheme = lightColorScheme(
    primary = EcoGreen,
    secondary = Terracotta,
    tertiary = Slate,
    background = SandSoft,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Slate,
    onSurface = Slate,
    surfaceVariant = EcoGreenLight,
    outline = BorderLine
)

@Composable
fun EcoPavéTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
