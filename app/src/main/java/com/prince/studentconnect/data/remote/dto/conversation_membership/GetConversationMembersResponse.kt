package com.prince.studentconnect.dto.conversation_membership

data class GetConversationMembersResponse(
    val members: Array<Member>
)

data class Member(
    val user_id: String,
    val role_in_conversation: String,
    val status: String
)
