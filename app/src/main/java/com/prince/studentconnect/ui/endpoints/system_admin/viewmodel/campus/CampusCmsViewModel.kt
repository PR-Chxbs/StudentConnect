package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.campus.AddCampusRequest
import com.prince.studentconnect.data.remote.dto.campus.Campus
import com.prince.studentconnect.data.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.data.remote.dto.campus.UpdateCampusRequest
import com.prince.studentconnect.data.repository.CampusRepository
import kotlinx.coroutines.launch

class CampusCmsViewModel(
    private val campusRepository: CampusRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val campuses: List<GetCampusesResponse>) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class OperationState {
        object Idle : OperationState()
        object Loading : OperationState()
        object Success : OperationState()
        data class Error(val message: String) : OperationState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    var operationState by mutableStateOf<OperationState>(OperationState.Idle)
        private set

    private var isInitialized = false
    lateinit var currentCampus: GetCampusesResponse

    fun initialize() {
        if (isInitialized) return
        isInitialized = true
        fetchCampuses()
    }

    fun fetchCampuses() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val response = campusRepository.getCampuses()
                if (response.isSuccessful) {
                    val campuses = response.body()?.toList().orEmpty()
                    uiState = UiState.Success(campuses)
                } else {
                    uiState = UiState.Error("Failed to load campuses: ${response.code()}")
                }
            } catch (e: Exception) {
                uiState = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun getCampusById(campusId: Int) {
        val currentState = uiState
        if (currentState is UiState.Success) {
            val campus = currentState.campuses.find { it.campus_id == campusId }
            if (campus != null) {
                currentCampus = campus
            } else {
                uiState = UiState.Error("Campus not found")
            }
        } else {
            uiState = UiState.Error("Campuses not loaded yet")
        }
    }

    fun deleteCampusById(campusId: Int) {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val response = campusRepository.deleteCampus(campusId)
                if (response.isSuccessful) {
                    fetchCampuses()
                } else {
                    uiState = UiState.Error("Failed to delete campus")
                }
            } catch (e: Exception) {
                uiState = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun addCampus(name: String, location: String, imageUrl: String) {
        viewModelScope.launch {
            operationState = OperationState.Loading
            try {
                val request = AddCampusRequest(name, location, imageUrl)
                val response = campusRepository.addCampus(request)
                if (response.isSuccessful) {
                    operationState = OperationState.Success
                    fetchCampuses()
                } else {
                    operationState = OperationState.Error("Failed to add campus")
                }
            } catch (e: Exception) {
                operationState = OperationState.Error("Error: ${e.message}")
            }
        }
    }

    fun updateCampus(campusId: Int, name: String, location: String, imageUrl: String) {
        viewModelScope.launch {
            operationState = OperationState.Loading
            try {
                val request = UpdateCampusRequest(name, location, imageUrl)
                val response = campusRepository.updateCampus(request, campusId)
                if (response.isSuccessful) {
                    operationState = OperationState.Success
                    fetchCampuses()
                } else {
                    operationState = OperationState.Error("Failed to update campus")
                }
            } catch (e: Exception) {
                operationState = OperationState.Error("Error: ${e.message}")
            }
        }
    }
}

