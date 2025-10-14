package com.prince.studentconnect.data.remote.dto.conversation

data class GetConversationResponse(
    val conversation_id: Int,
    val name: String,
    val type: String,
    val module_id: Int?,
    val visibility: String,
    val max_members: Int,
    val member_count: Int,
    val members: Array<Member>,
    val date_created: String
)

data class Member(
    val user_id: String,
    val role_in_conversation: String,
    val status: String
)
