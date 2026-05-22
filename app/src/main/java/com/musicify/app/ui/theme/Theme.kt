package com.musicify.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MusicifyPrimary,
    secondary = MusicifySecondary,
    tertiary = MusicifyTertiary,
    background = MusicifyBackground,
    surface = MusicifySurface,
    surfaceVariant = MusicifySurfaceVariant,
    onBackground = MusicifyOnBackground,
    onSurface = MusicifyOnSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = MusicifyPrimary,
    secondary = MusicifySecondary,
    tertiary = MusicifyTertiary,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun MusicifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
