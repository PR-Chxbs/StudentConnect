package com.prince.studentconnect.ui.endpoints.student.model.chat

import android.os.Build
import androidx.annotation.RequiresApi
import com.prince.studentconnect.data.remote.dto.conversation.Conversation
import com.prince.studentconnect.data.remote.dto.conversation.MemberA
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationType
import com.prince.studentconnect.utils.parseTimestamp
import java.time.Instant

data class ConversationUiModel(
    val id: Int,
    val name: String,
    val latestMessage: String,
    val latestMessageTimestamp: String,
    val latestMessageEpoch: Long,
    val profileImages: List<String>, // single student/lecturer or 3 for group
    val unreadCount: Int = 0, // >0 means unread indicator should show
    val type: ConversationType,
    val members: List<MemberUiModel>
)

data class MemberUiModel(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun Conversation.toUiModel(userId: String): ConversationUiModel {
    val conversationType = ConversationType.fromValue(type)

    var formatLastMessage: String = if (
        (conversationType == ConversationType.GROUP || conversationType == ConversationType.MODULE_DEFAULT)
        && lastMessage.senderName.isNotBlank()
    ) {
        "${lastMessage.senderName}: ${lastMessage.content}".take(50)
    } else {
        lastMessage.content.take(50)
    }

    formatLastMessage = if (formatLastMessage.length >= 50) {
        "$formatLastMessage..."
    } else {
        formatLastMessage
    }

    return ConversationUiModel(
        id = conversationId,
        name = when (conversationType) {
            ConversationType.PRIVATE_STUDENT,
            ConversationType.PRIVATE_LECTURER -> {
                val other = members.firstOrNull { it.userId != userId }
                other?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown"
            }
            ConversationType.GROUP -> name.ifBlank { "Unnamed Group" }
            ConversationType.MODULE_DEFAULT -> name.ifBlank { "Module Chat" }
        },
        latestMessage = formatLastMessage,
        latestMessageTimestamp = lastMessage.timestamp,
        latestMessageEpoch = parseTimestamp(lastMessage.timestamp),
        profileImages = when (conversationType) {
            ConversationType.GROUP, ConversationType.MODULE_DEFAULT ->
                members.take(3).map { it.profilePictureUrl }
            else -> {
                val other = members.firstOrNull { it.userId != userId }
                listOf(other?.profilePictureUrl ?: "")
            }
        },
        unreadCount = 0, // placeholder for now
        type = conversationType,
        members = members.map {
            MemberUiModel(
                userId = it.userId,
                firstName = it.firstName,
                lastName = it.lastName,
                profilePictureUrl = it.profilePictureUrl
            )
        }
    )
}
