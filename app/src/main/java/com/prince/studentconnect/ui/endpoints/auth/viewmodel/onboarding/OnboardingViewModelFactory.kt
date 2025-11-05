package com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.CampusRepository
import com.prince.studentconnect.data.repository.CourseRepository
import com.prince.studentconnect.data.repository.UserRepository

class OnboardingViewModelFactory(
    private val userRepository: UserRepository,
    private val campusRepository: CampusRepository,
    private val courseRepository: CourseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnboardingViewModel(
                userRepository = userRepository,
                campusRepository = campusRepository,
                courseRepository = courseRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}