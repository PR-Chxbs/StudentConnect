package com.prince.studentconnect.ui.endpoints.student.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.user.GetUserResponse
import com.prince.studentconnect.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userId: String
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val user: GetUserResponse) : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val response = userRepository.getUser(userId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        uiState = UiState.Success(it)
                    } ?: run {
                        uiState = UiState.Error("User not found")
                    }
                } else {
                    uiState = UiState.Error("Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                uiState = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
