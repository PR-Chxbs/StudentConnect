package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.user.User
import com.prince.studentconnect.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserCmsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    private var isInitialized = false

    fun initialize() {
        if (isInitialized) return

        loadUsers()
        isInitialized = true
    }

    fun loadUsers(role: String? = null, campusId: Int? = null, courseId: Int? = null) {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val response = userRepository.getUsers(role, campusId, courseId)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        uiState = UiState.Success(res.users.toList())
                    } ?: run {
                        uiState = UiState.Error("No users found")
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
