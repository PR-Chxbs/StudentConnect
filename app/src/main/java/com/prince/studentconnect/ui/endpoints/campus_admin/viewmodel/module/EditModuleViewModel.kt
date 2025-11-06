package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.module.CreateModuleRequest
import com.prince.studentconnect.data.remote.dto.module.GetModuleResponse
import com.prince.studentconnect.data.remote.dto.module.UpdateModuleRequest
import com.prince.studentconnect.data.repository.ModuleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EditModuleViewModel(
    private val moduleRepository: ModuleRepository
) : ViewModel() {

    data class UiState(
        val name: String = "",
        val code: String = "",
        val description: String = "",
        val isLoading: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun onCodeChange(newCode: String) {
        _uiState.value = _uiState.value.copy(code = newCode)
    }

    fun onDescriptionChange(newDesc: String) {
        _uiState.value = _uiState.value.copy(description = newDesc)
    }

    fun submitModule(isEditMode: Boolean, moduleId: Int? = null, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val currentState = _uiState.value
        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)
            try {
                if (isEditMode && moduleId != null) {
                    val request = UpdateModuleRequest(
                        currentState.name,
                        currentState.code,
                        currentState.description
                    )
                    val response = moduleRepository.updateModule(request, moduleId)
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Failed to update module: ${response.errorBody()?.string()}")
                    }
                } else {
                    val request = CreateModuleRequest(
                        currentState.name,
                        currentState.code,
                        currentState.description
                    )
                    val response = moduleRepository.addModule(request)
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Failed to create module: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: IOException) {
                onError("Network error: ${e.localizedMessage}")
            } catch (e: HttpException) {
                onError("HTTP error: ${e.localizedMessage}")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun populateFields(module: GetModuleResponse?) {
        module?.let {
            _uiState.value = UiState(
                name = it.name,
                code = it.code,
                description = it.description
            )
        }
    }

}