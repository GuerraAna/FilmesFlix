package com.br.natanfc.filmesflix.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BrandRed = Color(0xFFE50914)

private val DarkColors = darkColorScheme(
    primary = BrandRed,
    background = Color(0xFF141414),
    surface = Color(0xFF1F1F1F),
    onPrimary = Color.White,
    onBackground = Color(0xFFF5F5F5),
    onSurface = Color(0xFFF5F5F5)
)

private val LightColors = lightColorScheme(
    primary = BrandRed,
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color(0xFF1B1B1B),
    onSurface = Color(0xFF1B1B1B)
)

@Composable
fun FilmesFlixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
