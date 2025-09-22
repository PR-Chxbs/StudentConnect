package com.prince.studentconnect.ui.endpoints.student.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.conversation.Conversation
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.prince.studentconnect.data.repository.ConversationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class ConversationViewModel(
    private val conversationRepository: ConversationRepository,
) : ViewModel() {

    private lateinit var currentUserId: String
    private var isInitialized = false

    private val _conversations = MutableStateFlow<List<ConversationUiModel>>(emptyList())
    val conversations: StateFlow<List<ConversationUiModel>> = _conversations.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val allConversations = mutableListOf<ConversationUiModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun instantiate(currentUserId: String) {
        if (isInitialized) return  // don’t re-init if already done

        Log.d("ConversationViewModel", "(Instantiate): Instantiating ConversationViewModel")

        this.currentUserId = currentUserId
        isInitialized = true

        // Start collecting WebSocket messages automatically
        viewModelScope.launch {
            conversationRepository.incomingMessages.collect { message ->
                handleIncomingMessage(message)
            }
        }

        // You could even preload conversations here if you want
        loadConversations()
        Log.d("ConversationViewModel", "(Instantiate): Loading conversations")

        Log.d("ConversationViewModel", "(Instantiate): Conversations fetched...")
        Log.d("ConversationViewModel", "(Instantiate): $allConversations")

        // connect on startup
        connectWebSocket()
        Log.d("ConversationViewModel", "(Instantiate): Connecting Web Socket")
    }

    // ---------------- API loading ----------------
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadConversations(
        search: String? = null,
        type: String? = null,
        campusId: Int? = null
    ) {
        if (!isInitialized) return

        viewModelScope.launch {
            _loading.value = true
            try {
                val response = conversationRepository.getConversations(currentUserId, search, type, campusId)

                if (response.isSuccessful) {
                    val data: List<Conversation> = response.body()?.conversations?.toList() ?: emptyList()

                    Log.d("ConversationViewModel", "loadConversations() triggered $data")

                    allConversations.clear()
                    allConversations.addAll(data.map { it.toUiModel(currentUserId) }
                        .sortedByDescending { it.latestMessageEpoch }
                    )

                    _conversations.value = allConversations
                } else {
                    _error.value = "Failed to fetch conversations"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    // ---------------- WebSocket message handling ----------------
    @RequiresApi(Build.VERSION_CODES.O)
    fun handleIncomingMessage(message: SendMessageResponse) {
        _conversations.update { current ->
            current.map { conversation ->
                if (conversation.id == message.conversation_id) {
                    conversation.copy(
                        latestMessage = message.message_text.take(30),
                        latestMessageTimestamp = message.sent_at,
                        latestMessageEpoch = Instant.parse(message.sent_at).toEpochMilli(),
                        unreadCount = conversation.unreadCount + 1
                    )
                } else conversation
            }
                .sortedByDescending { it.latestMessageEpoch } // <-- ensure newest first
        }
    }

    // ---------------- Filtering ----------------
    /*fun showStudentConversations() {
        _conversations.value = allConversations.filter { it.type == ConversationType.PRIVATE_STUDENT }
    }

    fun showLecturerConversations() {
        _conversations.value = allConversations.filter { it.type == ConversationType.PRIVATE_LECTURER }
    }

    fun showGroupConversations() {
        _conversations.value = allConversations.filter {
            it.type == ConversationType.GROUP || it.type == ConversationType.MODULE_DEFAULT
        }
    }*/

    // ---------------- WebSocket lifecycle ----------------
    fun connectWebSocket() {
        if (isInitialized) conversationRepository.connect()
    }

    fun disconnectWebSocket() {
        if (isInitialized) conversationRepository.disconnect()
    }

    fun simulateMessageEmits() {
        viewModelScope.launch {
            conversationRepository.simulateMessageEmits()
        }
    }

    fun sendMessage(content: String, conversationId: Int) {
        if (!isInitialized) return
        viewModelScope.launch {
            val request = SendMessageRequest(
                sender_id = currentUserId,
                message_text = content,
                attachment_url = null,
                attachment_type = null
            )
            conversationRepository.sendMessageViaWebSocket(request)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectWebSocket()
    }
}


data class ConversationUiModel(
    val id: Int,
    val name: String,
    val latestMessage: String,
    val latestMessageTimestamp: String,
    val latestMessageEpoch: Long,
    val profileImages: List<String>, // single student/lecturer or 3 for group
    val unreadCount: Int = 0, // >0 means unread indicator should show
    val type: ConversationType
)

@RequiresApi(Build.VERSION_CODES.O)
fun Conversation.toUiModel(userId: String): ConversationUiModel {

    val instant = Instant.parse(this.lastMessage.timestamp) // backend value
    val epochMillis = instant.toEpochMilli()
    val conversationType = ConversationType.fromValue(type)

    var formatLastMessage: String = if ((conversationType == ConversationType.GROUP || conversationType == ConversationType.MODULE_DEFAULT)
        && lastMessage.senderName.isNotBlank()
    ) {
        "${lastMessage.senderName}: ${lastMessage.content}".take(50)
    } else {
        lastMessage.content.take(50)
    }

    formatLastMessage = if (formatLastMessage.length >= 50) {"$formatLastMessage..."} else {formatLastMessage}

    return ConversationUiModel(
        id = conversationId,
        name = when (conversationType) {
            ConversationType.PRIVATE_STUDENT,
            ConversationType.PRIVATE_LECTURER -> {
                // Pick the member who is NOT the current user
                val other = members.firstOrNull { it.userId != userId }
                other?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown"
            }
            ConversationType.GROUP -> name.ifBlank { "Unnamed Group" }
            ConversationType.MODULE_DEFAULT -> name.ifBlank { "Module Chat" } // Show module name or fallback
        },
        latestMessage = formatLastMessage,
        latestMessageTimestamp = lastMessage.timestamp,
        latestMessageEpoch = epochMillis,
        profileImages = when (conversationType) {
            ConversationType.GROUP, ConversationType.MODULE_DEFAULT ->
                members.take(3).map { it.profilePictureUrl }

            else -> {
                val other = members.firstOrNull { it.userId != userId }
                listOf(other?.profilePictureUrl ?: "")
            }
        },
        unreadCount = 0, // placeholder for now
        type = conversationType
    )
}



enum class ConversationType(val value: String) {
    PRIVATE_STUDENT("private_student"),
    PRIVATE_LECTURER("private_lecturer"),
    GROUP("group"),
    MODULE_DEFAULT("module_default");

    companion object {
        fun fromValue(value: String): ConversationType {
            return values().find { it.value == value } ?: GROUP
        }
    }
}
