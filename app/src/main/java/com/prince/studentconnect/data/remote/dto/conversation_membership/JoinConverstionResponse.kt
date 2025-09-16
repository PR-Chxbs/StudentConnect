package com.prince.studentconnect.data.remote.dto.conversation_membership

data class JoinConversationResponse(
    val conversation_member_id: Int,
    val user_id: String,
    val role_in_conversation: String,
    val status: String,
    val joined_at: String,
    val left_at: String?
)
