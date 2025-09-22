package com.prince.studentconnect.ui.endpoints.student.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.ConversationRepository

class ConversationViewModelFactory(
    private val conversationRepository: ConversationRepository,
    private val userId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConversationViewModel(conversationRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
