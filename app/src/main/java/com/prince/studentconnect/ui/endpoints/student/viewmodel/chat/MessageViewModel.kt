package com.prince.studentconnect.ui.endpoints.student.viewmodel.chat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.repository.ConversationRepository
import com.prince.studentconnect.ui.endpoints.student.model.chat.MessageUiModel
import com.prince.studentconnect.ui.endpoints.student.model.chat.toUiModel
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.ui.endpoints.student.model.chat.MemberUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MessageViewModel(
    private val repository: ConversationRepository,
    val userId: String,
    private val conversationId: Int,
    val members: List<MemberUiModel>, // now from ConversationUiModel
    val conversationName: String
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageUiModel>>(emptyList())
    val messages: StateFlow<List<MessageUiModel>> = _messages

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /** Derived UI properties */
    val isGroupConversation: Boolean = members.size > 2

    val otherUserProfile: String? =
        members.firstOrNull { it.userId != userId }?.profilePictureUrl

    val groupProfileImages: List<String> =
        if (isGroupConversation)
            members.filter { it.userId != userId }
                .take(3)
                .map { it.profilePictureUrl }
        else
            emptyList()

    init {
        loadMessages()
        observeIncomingMessages()
        // Log.d("MessageViewModel", "Members: $members")
        // Log.d("MessageViewModel", "Group Profile Images: $groupProfileImages")
    }

    fun loadMessages(limit: Int? = 50) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getMessagesInConversation(
                    conversationId = conversationId,
                    limit = limit
                )

                if (response.isSuccessful) {
                    val data = response.body()
                        ?.messages
                        ?.map { it.toUiModel(userId) }
                        .orEmpty()

                    // Log.d("MessageViewModel", "(loadMessages) ---------------- Unsorted ----------------\n${prettyPrintMessages(data)}\n\n")
                    _messages.value = data.sortedBy { it.sentAtEpoch }
                    // Log.d("MessageViewModel", "(loadMessages) ---------------- Sorted ---------------- ${prettyPrintMessages(_messages.value)}\n\n")
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
                    // Log.d("MessageViewModel", "(observeIncomingMessages) ----------- Alert -----------")
                    // Log.d("MessageViewModel", "(observeIncomingMessages) New message: $uiModel")
                    _messages.update { (it + uiModel).sortedBy { msg -> msg.sentAtEpoch } }
                    // Log.d("MessageViewModel", "(observeIncomingMessages) Sorted: ${_messages.value}")
                }
            }
        }
    }

    fun sendMessage(
        text: String,
        conversationId: Int,
        attachmentUrl: String? = null,
        attachmentType: String? = null
    ) {
        viewModelScope.launch {
            val request = SendMessageRequest(
                sender_id = userId,
                message_text = text,
                attachment_url = attachmentUrl,
                attachment_type = attachmentType
            )
            repository.sendMessage(request, conversationId)
            repository.sendMessageViaWebSocket(request, conversationId)
        }
    }

    fun prettyPrintMessages(messageList: List<MessageUiModel>): String {
        var returnString = ""
        var messageIndex = 1

        messageList.forEach { message ->
            returnString += "Message (${messageIndex++}): ${message.text}\nTime: ${message.sentAtTimestamp}\n\n"
        }

        return returnString
    }
}



