package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.campus.Campus
import com.prince.studentconnect.data.repository.CampusRepository
import kotlinx.coroutines.launch

class CampusCmsViewModel(
    private val campusRepository: CampusRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val campuses: List<Campus>) : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    private var isInitialized = false

    lateinit var currentCampus: Campus

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
                    val campuses = response.body()?.campus?.toList().orEmpty()
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
}
