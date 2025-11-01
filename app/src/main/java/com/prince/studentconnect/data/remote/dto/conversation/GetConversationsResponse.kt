package com.prince.studentconnect.data.remote.dto.conversation

import com.google.gson.annotations.SerializedName

data class GetConversationsResponse(
    val conversations: List<Conversation>
)

data class Conversation(
    @SerializedName("conversation_id") val conversationId: Int,
    val name: String,
    val type: String,
    @SerializedName("module_id") val moduleId: Int,
    val visibility: String,
    @SerializedName("max_members") val maxMembers: Int,
    @SerializedName("member_count") val memberCount: Int,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("last_message") val lastMessage: MessageA,
    val members: List<MemberA>
)

data class MessageA(
    @SerializedName("sender_id") val senderId: String,
    @SerializedName("sender_name") val senderName: String,
    val content: String,
    val timestamp: String
)

data class MemberA (
    @SerializedName("user_id") val userId: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("profile_picture_url") val profilePictureUrl: String
)