package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.user.GetUserResponse
import com.prince.studentconnect.data.remote.dto.user.GetUsersResponse
import com.prince.studentconnect.data.remote.dto.user.User
import com.prince.studentconnect.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserCmsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val users: List<GetUsersResponse>) : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    private var isInitialized = false

    var user: GetUserResponse? = null
    var errorMessage: String? = null

    fun initialize() {
        if (isInitialized) return
        Log.d("UserCmsViewModel", "(Campus Admin) First Initializing...")
        isInitialized = true

        loadUsers()
    }
    fun initialize(userId: String) {
        if (isInitialized) return
        isInitialized = true

        Log.d("UserCmsViewModel", "(Campus Admin) Second Initializing...")

        getUser(userId)
        loadUsers(campusId = user?.campus?.campus_id)
    }

    fun loadUsers(role: String? = null, campusId: Int? = null, courseId: Int? = null) {
        if (!isInitialized) return

        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val response = userRepository.getUsers(role, campusId, courseId)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        uiState = UiState.Success(res.toList())
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

    fun getUser(userId: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUser(userId)
                if (response.isSuccessful) {
                    user = response.body()
                    Log.d("UserCmsViewModel", "(Campus Admin) Found user: $user")
                } else {
                    errorMessage = "Failed to fetch user"
                    Log.e("UserCmsViewModel", errorMessage ?: "")
                }
            } catch (e: Exception) {
                errorMessage = "Error: $e"
                Log.e("UserCmsViewModel", "$errorMessage")
            }
        }
    }
}