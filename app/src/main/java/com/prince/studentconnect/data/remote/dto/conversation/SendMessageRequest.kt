package com.prince.studentconnect.data.remote.dto.conversation

data class SendMessageRequest(
    val sender_id: String,
    val message_text: String,
    val attachment_url: String?,
    val attachment_type: String?
)
