package com.prince.studentconnect.dto.conversation_membership

data class ChangeMemberRoleResponse(
    val conversation_member_id: Int,
    val role_in_conversation: String
)
