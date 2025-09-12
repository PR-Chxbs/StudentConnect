package com.prince.studentconnect.remote.dto.conversation

data class CreateConversationRequest(
    val name: String,
    val type: String,
    val module_id: Int,
    val visibility: String,
    val max_members: Int,
    val initial_members: Array<String>
)
