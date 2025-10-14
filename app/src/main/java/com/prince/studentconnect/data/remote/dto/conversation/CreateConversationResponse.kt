package com.prince.studentconnect.data.remote.dto.conversation

data class CreateConversationResponse(
    val conversation_id: Int,
    val name: String,
    val type: String,
    val module_id: Int,
    val visibility: String,
    val max_members: Int,
    val member_count: Int,
    val date_created: String
)
