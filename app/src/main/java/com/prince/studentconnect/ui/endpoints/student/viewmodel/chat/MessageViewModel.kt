package com.prince.studentconnect.ui.endpoints.student.viewmodel.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.repository.ConversationRepository
import com.prince.studentconnect.ui.endpoints.student.model.chat.MessageUiModel
import com.prince.studentconnect.ui.endpoints.student.model.chat.toUiModel
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MessageViewModel(
    private val repository: ConversationRepository,
    private val userId: String,
    private val conversationId: Int
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageUiModel>>(emptyList())
    val messages: StateFlow<List<MessageUiModel>> = _messages

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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
                        ?.sortedBy { it.sentAtEpoch }
                        ?: emptyList()

                    _messages.value = data
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
}

