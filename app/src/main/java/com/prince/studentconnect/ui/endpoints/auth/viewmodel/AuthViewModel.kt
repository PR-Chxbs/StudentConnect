package com.prince.studentconnect.ui.endpoints.auth.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.AuthRepository
import com.prince.studentconnect.data.repository.AuthResult
import com.prince.studentconnect.data.repository.UserRepository
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
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            successMessage = "Logged in successfully"
                        )
                        redirectScreenRoute = Screen.Student.route
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
                Log.e("AuthViewModel", "Error: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun loginWithGoogle(context: Context) {
        val result = authRepository.loginWithGoogle(context)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            successMessage = if (result is AuthResult.Success) "Redirecting to Google login..." else null,
            errorMessage = if (result is AuthResult.Error) result.message else null
        )
    }
}
