package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.course.CreateCourseRequest
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse
import com.prince.studentconnect.data.remote.dto.relationship.LinkCourseModuleRequest
import com.prince.studentconnect.data.repository.CourseRepository
import com.prince.studentconnect.data.repository.ModuleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateCourseViewModel(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository
) : ViewModel() {

    var courseName = MutableStateFlow("")
    var courseDescription = MutableStateFlow("")
    var durationYears = MutableStateFlow("")

    private val _modules = MutableStateFlow<List<GetModulesResponse>>(emptyList())
    val modules: StateFlow<List<GetModulesResponse>> = _modules

    private val _selectedModuleIds = MutableStateFlow<List<Int>>(emptyList())
    val selectedModuleIds: StateFlow<List<Int>> = _selectedModuleIds

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun toggleModuleSelection(moduleId: Int) {
        val current = _selectedModuleIds.value.toMutableList()
        if (current.contains(moduleId)) {
            current.remove(moduleId)
        } else {
            current.add(moduleId)
        }
        _selectedModuleIds.value = current
    }

    fun loadModules() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = moduleRepository.getModules(null, null)
                if (response.isSuccessful && response.body() != null) {
                    _modules.value = response.body()!!
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createCourse(
        campusId: Int = 1,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = CreateCourseRequest(
                    name = courseName.value,
                    description = courseDescription.value,
                    duration_years = durationYears.value.toIntOrNull() ?: 1,
                    campus_id = campusId
                )

                val response = courseRepository.addCourse(request)
                if (response.isSuccessful && response.body() != null) {
                    val course = response.body()!!
                    linkModulesToCourse(course.course_id, onSuccess, onError)
                } else {
                    onError("Failed to create course.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun linkModulesToCourse(
        courseId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                for (moduleId in _selectedModuleIds.value) {
                    val linkRequest = LinkCourseModuleRequest(course_id = courseId, module_id = moduleId, year_level = 0)
                    val response = courseRepository.linkCourseModule(linkRequest)
                    if (!response.isSuccessful) {
                        onError("Failed to link module $moduleId.")
                        return@launch
                    }
                }
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred while linking modules.")
            }
        }
    }
}
