package com.prince.studentconnect.ui.endpoints.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.AuthRepository
import com.prince.studentconnect.data.repository.NotificationRepository
import com.prince.studentconnect.data.repository.UserRepository

class AuthViewModelFactory(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(
                authRepository = authRepository,
                userRepository = userRepository,
                notificationRepository = notificationRepository,
                userPrefs = userPrefs
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}