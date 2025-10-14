package com.prince.studentconnect.ui.endpoints.student.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.preferences.ThemePreferenceManager

class SettingsViewModelFactory(
    private val themeManager: ThemePreferenceManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(themeManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
