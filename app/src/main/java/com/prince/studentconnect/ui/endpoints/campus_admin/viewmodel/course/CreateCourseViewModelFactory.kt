package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.CourseRepository
import com.prince.studentconnect.data.repository.ModuleRepository

class CreateCourseViewModelFactory(
    private val courseRepository: CourseRepository,
    private val moduleRepository: ModuleRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateCourseViewModel(
                courseRepository = courseRepository,
                moduleRepository = moduleRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}