package com.prince.studentconnect.dto.conversation_membership

data class RejoinConversationResponse(
    val conversation_member_id: Int,
    val status: String,
    val joined_at: String
)
