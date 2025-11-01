package com.prince.studentconnect.data.remote.dto.conversation

data class GetMessagesResponse(
    val message_id: Int,
    val sender_id: String,
    val message_text: String,
    val attachment_url: String?,
    val sent_at: String
)

data class Message(
    val message_id: Int,
    val sender_id: String,
    val message_text: String,
    val attachment_url: String?,
    val sent_at: String
)
