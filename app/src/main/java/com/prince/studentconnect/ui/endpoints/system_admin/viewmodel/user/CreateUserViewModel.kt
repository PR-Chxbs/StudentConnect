package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user

import com.prince.studentconnect.data.repository.AuthRepository
import com.prince.studentconnect.data.repository.UserRepository
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.user.CreateUserRequest
import com.prince.studentconnect.data.repository.AuthResult
import com.prince.studentconnect.data.repository.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class CreateUserViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Idle)
        private set

    var isSupabaseCreated = false

    // --- Form fields ---
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var firstName by mutableStateOf("")
    var middleName by mutableStateOf<String?>(null)
    var lastName by mutableStateOf("")
    var studentNumber by mutableStateOf<String?>(null)
    var phoneNumber by mutableStateOf("")
    var role by mutableStateOf("")
    var bio by mutableStateOf("")
    var campusId by mutableStateOf<Int?>(null)
    var courseId by mutableStateOf<Int?>(null)
    var profilePictureUrl by mutableStateOf("")

    fun createUser() {
        viewModelScope.launch {
            uiState = UiState.Loading

            // Step 0: Validate password fields
            if (password != confirmPassword) {
                uiState = UiState.Error("Passwords do not match.")
                return@launch
            }

            if (!isPasswordStrong(password)) {
                uiState = UiState.Error("Password must be at least 8 characters long and contain letters and numbers.")
                return@launch
            }

            try {
                // Step 1: Sign up in Supabase
                val authResult = authRepository.signUp(email, password)

                when (authResult) {
                    is AuthResult.Success -> {
                        // ✅ Get actual Supabase user ID
                        val session = SupabaseClientProvider.client.auth.currentSessionOrNull()
                        val supabaseUserId = session?.user?.id

                        if (supabaseUserId == null) {
                            uiState = UiState.Error("Could not retrieve user ID from Supabase.")
                            return@launch
                        }

                        // Step 2: Create user in your backend
                        val createUserRequest = CreateUserRequest(
                            user_id = supabaseUserId,
                            first_name = firstName,
                            middle_name = middleName,
                            last_name = lastName,
                            student_number = studentNumber,
                            email = email,
                            phone_number = phoneNumber,
                            role = role,
                            bio = bio,
                            campus_id = campusId,
                            course_id = courseId,
                            profile_picture_url = profilePictureUrl
                        )

                        val response = userRepository.createUser(createUserRequest)

                        if (response.isSuccessful) {
                            uiState = UiState.Success
                        } else {
                            uiState = UiState.Error("Backend user creation failed: ${response.code()}")
                        }
                    }

                    is AuthResult.RequiresEmailConfirmation -> {
                        uiState = UiState.Error("User must confirm email before activation.")
                    }

                    is AuthResult.Error -> {
                        uiState = UiState.Error("Supabase signup failed: ${authResult.message}")
                    }
                }

            } catch (e: Exception) {
                uiState = UiState.Error("Unexpected error: ${e.message}")
            }
        }
    }

    private fun isPasswordStrong(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= 8 && hasLetter && hasDigit
    }
}
