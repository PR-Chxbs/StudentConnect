package com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.CampusRepository

class CampusCmsViewModelFactory(
    private val campusRepository: CampusRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampusCmsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CampusCmsViewModel(
                campusRepository = campusRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}