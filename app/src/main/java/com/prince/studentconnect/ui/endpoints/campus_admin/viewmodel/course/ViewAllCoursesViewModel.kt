package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CourseUiState {
    object Loading : CourseUiState()
    data class Success(val courses: List<GetCoursesResponse>) : CourseUiState()
    data class Error(val message: String) : CourseUiState()
}

class ViewAllCoursesViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState

    private val _isDeleting = MutableStateFlow(false)
    val isDeleting: StateFlow<Boolean> = _isDeleting

    fun loadCourses(campusId: Int = 1) {
        _uiState.value = CourseUiState.Loading
        viewModelScope.launch {
            try {
                val response = courseRepository.getCourses(campusId)
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = CourseUiState.Success(response.body()!!)
                } else {
                    _uiState.value = CourseUiState.Error("Failed to load courses.")
                }
            } catch (e: Exception) {
                _uiState.value = CourseUiState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun deleteCourse(courseId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isDeleting.value = true
            try {
                val response = courseRepository.deleteCourse(courseId)
                if (response.isSuccessful) {
                    onSuccess()
                    loadCourses() // refresh list
                } else {
                    onError("Failed to delete course.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred.")
            } finally {
                _isDeleting.value = false
            }
        }
    }
}
