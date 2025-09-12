package com.prince.studentconnect.remote.dto.conversation

data class AddConversationMemberResponse(
    val conversation_member_id: Int,
    val user_id: String,
    val role_in_conversation: String,
    val status: String,
    val joined_at: String
)
