package com.prince.studentconnect.ui.endpoints.student.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.conversation.Conversation
import com.prince.studentconnect.data.repository.ConversationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val conversationRepository: ConversationRepository
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<ConversationUiModel>>(emptyList())
    val conversations: StateFlow<List<ConversationUiModel>> = _conversations.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val allConversations = mutableListOf<ConversationUiModel>()

    fun loadConversations(
        userId: String,
        search: String? = null,
        type: String? = null,
        campusId: Int? = null
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = conversationRepository.getConversations(userId, search, type, campusId)

                if (response.isSuccessful) {
                    val data: List<Conversation> = response.body()?.conversations?.toList() ?: emptyList()

                    allConversations.clear()
                    allConversations.addAll(data.map { it.toUiModel() })

                    // By default: show all
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

    fun showStudentConversations() {
        _conversations.value = allConversations.filter { it.type == ConversationType.PRIVATE_STUDENT }
    }

    fun showLecturerConversations() {
        _conversations.value = allConversations.filter { it.type == ConversationType.PRIVATE_LECTURER }
    }

    fun showGroupConversations() {
        _conversations.value = allConversations.filter {
            it.type == ConversationType.GROUP || it.type == ConversationType.MODULE_DEFAULT
        }
    }



}

data class ConversationUiModel(
    val id: Int,
    val name: String,
    val latestMessage: String,
    val latestMessageTimestamp: String,
    val profileImages: List<String>, // single student/lecturer or 3 for group
    val unreadCount: Int = 0, // >0 means unread indicator should show
    val type: ConversationType
)

fun Conversation.toUiModel(): ConversationUiModel {
    val conversationType = ConversationType.fromValue(type)

    return ConversationUiModel(
        id = conversationId,
        name = when (conversationType) {
            ConversationType.PRIVATE_STUDENT -> members.firstOrNull()?.let { "${it.firstName} ${it.lastName}" } ?: "Student"
            ConversationType.PRIVATE_LECTURER -> members.firstOrNull()?.let { "${it.firstName} ${it.lastName}" } ?: "Lecturer"
            ConversationType.GROUP -> name.ifBlank { "Unnamed Group" }
            ConversationType.MODULE_DEFAULT -> name.ifBlank { "Module Chat" } // Show module name or fallback
        },
        latestMessage = if ((conversationType == ConversationType.GROUP || conversationType == ConversationType.MODULE_DEFAULT)
            && lastMessage.senderName.isNotBlank()
        ) {
            "${lastMessage.senderName}: ${lastMessage.content.take(30)}"
        } else {
            lastMessage.content.take(30)
        },
        latestMessageTimestamp = lastMessage.timestamp,
        profileImages = when (conversationType) {
            ConversationType.GROUP, ConversationType.MODULE_DEFAULT -> members.take(3).map { it.profilePictureUrl }
            else -> listOf(members.firstOrNull()?.profilePictureUrl ?: "")
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
