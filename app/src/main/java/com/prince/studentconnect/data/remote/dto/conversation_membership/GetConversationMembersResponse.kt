package com.prince.studentconnect.data.remote.dto.conversation_membership

import com.prince.studentconnect.data.remote.dto.conversation.Member

data class GetConversationMembersResponse(
    val members: Array<Member>
)

