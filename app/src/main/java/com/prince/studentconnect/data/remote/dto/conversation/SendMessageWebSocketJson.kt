package com.prince.studentconnect.data.remote.dto.conversation

data class SendMessageWebSocketJson (
    val sender_id: String,
    val message_text: String,
    val attachment_url: String?,
    val attachment_type: String?,
    val conversation_id: Int
)