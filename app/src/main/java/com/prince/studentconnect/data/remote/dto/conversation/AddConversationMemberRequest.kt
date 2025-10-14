package com.prince.studentconnect.data.remote.dto.conversation

data class AddConversationMemberRequest(
    val user_id: String,
    val role_in_conversation: String
)
