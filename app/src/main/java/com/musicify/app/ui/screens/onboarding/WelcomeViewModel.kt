package com.musicify.app.ui.screens.onboarding

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("musicify_prefs", Context.MODE_PRIVATE)
    
    private val _showOnboarding = MutableStateFlow(!prefs.getBoolean("onboarding_done", false))
    val showOnboarding: StateFlow<Boolean> = _showOnboarding.asStateFlow()

    fun completeOnboarding() {
        prefs.edit().putBoolean("onboarding_done", true).apply()
        _showOnboarding.value = false
    }
}
