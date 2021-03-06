package com.bafoor.dailymeal.presentation.ui.recipe_list.component.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
        surface = Black1,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    DarkTheme : Boolean,
    content : @Composable  () -> Unit
) {
    MaterialTheme(
        colors = if (DarkTheme) DarkThemeColors else LightThemeColors,
    ) {
        Box(
            modifier =
            Modifier.fillMaxSize()
                .background(color = if (!DarkTheme) Grey1 else Color.Black)
        ) {
            content()
        }
    }
}















