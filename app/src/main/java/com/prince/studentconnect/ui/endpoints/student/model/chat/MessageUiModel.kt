package com.prince.studentconnect.ui.endpoints.student.model.chat

import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.prince.studentconnect.data.remote.fakeapi.FakeConversationApi.InternalMessage

data class MessageUiModel(
    val id: Int,
    val conversationId: Int,
    val senderId: String,
    val text: String,
    val attachmentUrl: String?,
    val attachmentType: String?,
    val sentAt: String,
    val isMine: Boolean // derived from comparing senderId with currentUserId
)

fun SendMessageResponse.toUiModel(currentUserId: String): MessageUiModel {
    return MessageUiModel(
        id = message_id,
        conversationId = conversation_id,
        senderId = sender_id,
        text = message_text,
        attachmentUrl = attachment_url,
        attachmentType = attachment_type,
        sentAt = sent_at,
        isMine = sender_id == currentUserId
    )
}

fun InternalMessage.toUiModel(currentUserId: String): MessageUiModel {
    return MessageUiModel(
        id = message_id,
        conversationId = conversation_id,
        senderId = sender_id,
        text = message_text,
        attachmentUrl = attachment_url,
        attachmentType = attachment_type,
        sentAt = sent_at,
        isMine = sender_id == currentUserId
    )
}
