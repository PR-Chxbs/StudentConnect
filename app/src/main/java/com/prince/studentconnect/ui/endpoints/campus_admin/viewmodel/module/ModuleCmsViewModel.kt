package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse
import com.prince.studentconnect.data.repository.ModuleRepository
import kotlinx.coroutines.launch

class ModuleCmsViewModel(
    private val moduleRepository: ModuleRepository
) : ViewModel() {

    sealed class UiState {
        object Loading : UiState()
        data class Success(val modules: List<GetModulesResponse>) : UiState()
        data class Error(val message: String) : UiState()
    }

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    var selectedModule by mutableStateOf<GetModulesResponse?>(null)
        private set

    fun fetchModules() {
        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val response = moduleRepository.getModules(null, null)
                if (response.isSuccessful) {
                    val modules = response.body().orEmpty()
                    if (modules.isEmpty()) {
                        uiState = UiState.Error("No modules found.")
                    } else {
                        uiState = UiState.Success(modules)
                    }
                } else {
                    uiState = UiState.Error("Failed to load modules: ${response.code()}")
                }
            } catch (e: Exception) {
                uiState = UiState.Error("Error fetching modules: ${e.message}")
            }
        }
    }

    fun selectModule(module: GetModulesResponse) {
        selectedModule = module
    }

    fun deleteModule(moduleId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = moduleRepository.deleteModule(moduleId)
                if (response.isSuccessful) {
                    onSuccess()
                    fetchModules() // refresh list
                } else {
                    onError("Failed to delete module.")
                }
            } catch (e: Exception) {
                onError("Error deleting module: ${e.message}")
            }
        }
    }
}