package com.prince.studentconnect.ui.endpoints.auth.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.AuthRepository
import com.prince.studentconnect.data.repository.AuthResult
import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenRequest
import com.prince.studentconnect.data.remote.dto.notification.CreateDeviceTokenResponse
import com.prince.studentconnect.data.remote.dto.user.GetUserResponse
import com.prince.studentconnect.data.repository.NotificationRepository
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
    private val notificationRepository: NotificationRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _currentUserId = MutableStateFlow("")
    val currentUserId: StateFlow<String> = _currentUserId

    private val _deviceToken = MutableStateFlow("Fetching token...")
    val deviceToken: StateFlow<String> = _deviceToken

    private val _currentUserEmail = MutableStateFlow("")
    val currentUserEmail: StateFlow<String?> = _currentUserEmail

    lateinit var currentUser: GetUserResponse

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
                        val currentUser = authRepository.getCurrentUser()
                        val userId = currentUser?.id

                        // Log.d("AuthScreen", "(AuthViewModel) Current user: ${authRepository.getCurrentUser()}")

                        if (userId != null) {
                            userPrefs.saveUserId(userId)
                            _currentUserId.value = userId

                            setNewDeviceTokenCall()

                            val userDetails = userRepository.getUser(userId).body()
                            // Log.d("AuthScreen", "(AuthViewModel) User details from api: $userDetails")
                            if (userDetails == null) {

                                if (currentUser.email == null) {
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        errorMessage = "Unexpected result"
                                    )
                                    return@launch
                                }

                                _currentUserEmail.value = currentUser.email ?: ""

                                Log.d("AuthScreen", "(AuthViewModel) Email: ${_currentUserEmail.value}\n                User Id: ${_currentUserId.value}")

                                redirectScreenRoute = Screen.OnboardingPersonalDetails.route
                            } else {
                                when (userDetails.role) {
                                    "student" -> redirectScreenRoute = Screen.Student.route
                                    "campus_admin" -> redirectScreenRoute = Screen.CampusAdmin.route
                                    "system_admin" -> redirectScreenRoute = Screen.SystemAdmin.route
                                    "lecturer" -> redirectScreenRoute = Screen.Lecturer.route
                                }
                            }
                        }

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

    fun register() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = authRepository.signUp(email, password)) {
                is AuthResult.Success -> {
                    val currentUser = authRepository.getCurrentUser()
                    val userId = currentUser?.id

                    if (currentUser?.email == null || userId == null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "Unexpected result"
                        )
                        return@launch
                    }

                    _currentUserId.value = userId
                    _currentUserEmail.value = currentUser.email ?: ""

                    setNewDeviceTokenCall()

                    // Log.d("AuthScreen", "(AuthViewModel) Email: ${_currentUserEmail.value}\n                User Id: ${_currentUserId.value}")

                    userPrefs.saveUserId(userId)

                    redirectScreenRoute = Screen.OnboardingPersonalDetails.route

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

    fun setNewDeviceToken(newDeviceToken: String) {
        _deviceToken.value = newDeviceToken
    }

    private suspend fun setNewDeviceTokenCall() {
        val createDeviceTokenRequest = CreateDeviceTokenRequest(
            device_token = _deviceToken.value,
            user_id = _currentUserId.value
        )

        val response = notificationRepository.createDeviceToken(createDeviceTokenRequest)

        if (response.isSuccessful) {
            Log.d("FCM", "(AuthViewModel) Token successfully created")
        } else {
            val errorMessage = response.errorBody()?.string()
            Log.d("FCM", "(AuthViewModel) Error: $errorMessage")
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
