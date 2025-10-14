package com.prince.studentconnect.ui.endpoints.student.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.preferences.ThemePreferenceManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeManager: ThemePreferenceManager) : ViewModel() {

    val themeMode = themeManager.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setTheme(mode: Int) {
        viewModelScope.launch {
            themeManager.setThemeMode(mode)
        }
    }
}
