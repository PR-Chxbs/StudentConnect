package com.prince.studentconnect.ui.endpoints.student.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.AuthRepository

class SettingsViewModelFactory(
    private val themeManager: UserPreferencesRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(themeManager, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
