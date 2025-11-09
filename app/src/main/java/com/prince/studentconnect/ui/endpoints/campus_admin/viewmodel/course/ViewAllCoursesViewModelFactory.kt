package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.CourseRepository

class ViewAllCoursesViewModelFactory(
    private val courseRepository: CourseRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewAllCoursesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewAllCoursesViewModel(
                courseRepository = courseRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}