package com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.ModuleRepository

class ModuleViewModelFactory(
    private val moduleRepository: ModuleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModuleViewModel(
                moduleRepository = moduleRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}