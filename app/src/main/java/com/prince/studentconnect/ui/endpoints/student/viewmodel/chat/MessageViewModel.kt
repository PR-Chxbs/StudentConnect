package com.prince.studentconnect.ui.endpoints.student.viewmodel.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.conversation.MemberA
import com.prince.studentconnect.data.repository.ConversationRepository
import com.prince.studentconnect.ui.endpoints.student.model.chat.MessageUiModel
import com.prince.studentconnect.ui.endpoints.student.model.chat.toUiModel
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class MessageViewModel(
    private val repository: ConversationRepository,
    private val userId: String,
    private val conversationId: Int,
    private val members: List<MemberA> // pass conversation members here for profile images
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageUiModel>>(emptyList())
    val messages: StateFlow<List<MessageUiModel>> = _messages

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /** Derived properties for profile images */
    val otherUserProfile: String? =
        members.firstOrNull { it.userId != userId }?.profilePictureUrl

    val groupProfileImages: List<String> =
        members.filter { it.userId != userId }.take(3).map { it.profilePictureUrl }

    init {
        loadMessages()
        observeIncomingMessages()
    }

    fun loadMessages(limit: Int? = 50) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getMessagesInConversation(conversationId, limit = limit)
                if (response.isSuccessful) {
                    val data = response.body()
                        ?.messages
                        ?.map { it.toUiModel(userId) }
                        ?: emptyList()
                    _messages.value = data.sortedBy { it.sentAtEpoch } // chronological
                } else {
                    _error.value = "Failed to fetch messages: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeIncomingMessages() {
        viewModelScope.launch {
            repository.incomingMessages.collect { newMessage ->
                if (newMessage.conversation_id == conversationId) {
                    val uiModel = newMessage.toUiModel(userId)
                    _messages.update { (it + uiModel).sortedBy { msg -> msg.sentAtEpoch } }
                }
            }
        }
    }

    fun sendMessage(text: String, attachmentUrl: String? = null, attachmentType: String? = null) {
        viewModelScope.launch {
            val request = SendMessageRequest(
                sender_id = userId,
                message_text = text,
                attachment_url = attachmentUrl,
                attachment_type = attachmentType
            )
            repository.sendMessageViaWebSocket(request)
        }
    }

    /** Utility to group messages by date (for separators) */
    fun getMessagesGroupedByDate(): Map<String, List<MessageUiModel>> {
        return _messages.value.groupBy { msg ->
            val msgDate = Instant.ofEpochMilli(msg.sentAtEpoch).atZone(ZoneId.systemDefault()).toLocalDate()
            val today = LocalDate.now()
            val yesterday = today.minusDays(1)

            when (msgDate) {
                today -> "Today"
                yesterday -> "Yesterday"
                else -> msgDate.dayOfWeek.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                } // Sunday, Monday, etc.
            }
        }
    }
}


