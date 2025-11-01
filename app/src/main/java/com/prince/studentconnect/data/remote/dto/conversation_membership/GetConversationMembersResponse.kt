package com.prince.studentconnect.data.remote.dto.conversation_membership

data class GetConversationMembersResponse(
    val user_id: String,
    val role_in_conversation: String,
    val status: String
)

