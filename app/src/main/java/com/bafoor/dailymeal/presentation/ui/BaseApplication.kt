package com.bafoor.dailymeal.presentation.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    val isDark = mutableStateOf(false)

    fun onToggleTheme() {
        isDark.value = !isDark.value
    }
}