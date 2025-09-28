package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.UserRepository

class UserCmsViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserCmsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserCmsViewModel(
                userRepository = userRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}