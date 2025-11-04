package com.prince.studentconnect.ui.endpoints.student.viewmodel.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.UserRepository

class ProfileViewModelFactory(
    private val userRepository: UserRepository,
    private val userId: String
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                userRepository = userRepository,
                userId = userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}