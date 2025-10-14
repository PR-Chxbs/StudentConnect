package com.prince.studentconnect.data.remote.dto.conversation

data class GetConversationsResponse(
    val conversations: Array<Conversation>
)

data class Conversation(
    val conversationId: Int,
    val name: String,
    val type: String,
    val moduleId: Int,
    val visibility: String,
    val maxMembers: Int,
    val memberCount: Int,
    val dateCreated: String,
    val lastMessage: MessageA,
    val members: Array<MemberA>
)

data class MessageA(
    val senderId: String,
    val senderName: String,
    val content: String,
    val timestamp: String
)

data class MemberA (
    val userId: String,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String
)