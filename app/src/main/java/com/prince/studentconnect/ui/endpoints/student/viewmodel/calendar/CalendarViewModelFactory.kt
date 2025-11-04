package com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.EventRepository

class CalendarViewModelFactory(
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository = eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}