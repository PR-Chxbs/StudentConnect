package com.prince.studentconnect.ui.endpoints.student.viewmodel.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.repository.ConversationRepository
import com.prince.studentconnect.ui.endpoints.student.model.chat.MemberUiModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationType

class MessageViewModelFactory(
    private val conversationRepository: ConversationRepository,
    private val userId: String,
    private val conversationId: Int,
    private val members: List<MemberUiModel>,
    private val conversationName: String,
    private val conversationType: ConversationType
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(
                repository = conversationRepository,
                userId = userId,
                conversationId = conversationId,
                members = members,
                conversationName = conversationName,
                conversationType = conversationType
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}