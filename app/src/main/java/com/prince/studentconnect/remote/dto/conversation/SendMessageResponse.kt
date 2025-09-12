package com.prince.studentconnect.remote.dto.conversation

data class SendMessageResponse(
    val message_id: Int,
    val conversation_id: Int,
    val sender_id: String,
    val message_text: String,
    val attachment_url: String?,
    val attachment_type: String?,
    val sent_at: String
)
