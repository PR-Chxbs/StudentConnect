package com.prince.studentconnect.ui.endpoints.student.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPrefs: UserPreferencesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val themeMode = userPrefs.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setTheme(mode: Int) {
        viewModelScope.launch {
            userPrefs.setThemeMode(mode)
        }
    }

    fun saveLanguage(lang: String) {
        viewModelScope.launch {
            userPrefs.saveLanguage(lang)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            userPrefs.clearUserId()
            userPrefs.clearRole()
        }
    }
}
