package com.prince.studentconnect.data.remote.dto.conversation_membership

data class LeaveConversationResponse(
    val conversation_member_id: Int,
    val status: String,
    val left_at: String
)
