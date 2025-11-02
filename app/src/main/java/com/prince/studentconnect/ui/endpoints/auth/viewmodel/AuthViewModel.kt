package com.prince.studentconnect.ui.endpoints.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.repository.AuthRepository
import com.prince.studentconnect.data.repository.AuthResult
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.UserRepository
import com.prince.studentconnect.navigation.Graph
import com.prince.studentconnect.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _currentUserId = MutableStateFlow("")
    val currentUserId: StateFlow<String> = _currentUserId

    lateinit var redirectScreenRoute: String

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                when (val result = authRepository.login(email, password)) {
                    is AuthResult.Success -> {
                        val userId = authRepository.getCurrentUser()?.id
                        if (userId != null) {
                            userPrefs.saveUserId(userId)
                            _currentUserId.value = userId
                            val userDetails = userRepository.getUser(userId).body()
                            when (userDetails?.role) {
                                "student" -> redirectScreenRoute = Screen.Student.route
                                "campus_admin" -> redirectScreenRoute = Screen.CampusAdmin.route
                                "system_admin" -> redirectScreenRoute = Screen.SystemAdmin.route
                                "lecturer" -> redirectScreenRoute = Screen.Lecturer.route
                            }
                        }

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            successMessage = "Welcome, ${result.email}"
                        )
                    }
                    is AuthResult.Error -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                    else -> _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Unexpected result"
                    )
                }
            } catch (e: Exception) {
                Log.e("AuthScreen", "(AuthViewModel) Error: ${e.message}")
            }

        }
    }

    fun register() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = authRepository.signUp(email, password)) {
                is AuthResult.Success -> {
                    val userId = authRepository.getCurrentUser()?.id
                    if (userId != null) userPrefs.saveUserId(userId)

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Registration successful!"
                    )
                }

                is AuthResult.RequiresEmailConfirmation -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Please confirm your email."
                )
                is AuthResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }
}
