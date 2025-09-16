package com.prince.studentconnect.data.remote.fakeapi

import android.os.Build
import androidx.annotation.RequiresApi
import com.prince.studentconnect.data.remote.api.ConversationApi
import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.time.Instant

class FakeConversationApi : ConversationApi {

    private val conversations = mutableListOf<InternalConversation>()
    private var nextConversationId = 1
    private var nextMessageId = 1
    private var nextConversationMemberId = 1

    private data class InternalMessage(
        val message_id: Int,
        val sender_id: String,
        val message_text: String,
        val attachment_url: String?,
        val attachment_type: String?,
        val sent_at: String
    )

    private data class InternalMember(
        val user_id: String,
        var role_in_conversation: String = "member",
        var status: String = "active",
        var joined_at: String,
        var left_at: String? = null
    )

    private data class InternalConversation(
        val conversation_id: Int,
        val name: String,
        val type: String,
        val module_id: Int?,
        val visibility: String,
        val max_members: Int,
        val members: MutableList<InternalMember>,
        val date_created: String,
        val messages: MutableList<InternalMessage> = mutableListOf()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createConversation(request: CreateConversationRequest): Response<CreateConversationResponse> {
        val id = nextConversationId++
        val now = Instant.now().toString()
        val members = request.initial_members.map { memberId ->
            InternalMember(user_id = memberId, joined_at = now)
        }.toMutableList()

        val internal = InternalConversation(
            conversation_id = id,
            name = request.name,
            type = request.type,
            module_id = request.module_id,
            visibility = request.visibility,
            max_members = request.max_members,
            members = members,
            date_created = now
        )
        conversations.add(internal)

        val response = CreateConversationResponse(
            conversation_id = id,
            name = request.name,
            type = request.type,
            module_id = request.module_id,
            visibility = request.visibility,
            max_members = request.max_members,
            member_count = members.size,
            date_created = now
        )
        return Response.success(response)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addConversationMember(
        request: AddConversationMemberRequest,
        conversationId: Int
    ): Response<AddConversationMemberResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }

        return if (conversation != null) {
            val existing = conversation.members.find { it.user_id == request.user_id }
            if (existing != null) {
                Response.error(
                    400,
                    """{"error":"User is already a member"}""".toResponseBody("application/json".toMediaType())
                )
            } else {
                val now = Instant.now().toString()
                val member = InternalMember(
                    user_id = request.user_id,
                    role_in_conversation = request.role_in_conversation,
                    joined_at = now
                )
                conversation.members.add(member)
                Response.success(
                    AddConversationMemberResponse(
                        conversation_member_id = nextConversationMemberId++,
                        user_id = member.user_id,
                        role_in_conversation = member.role_in_conversation,
                        status = member.status,
                        joined_at = member.joined_at
                    )
                )
            }
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    override suspend fun removeConversationMember(
        conversationId: Int,
        userId: String
    ): Response<Unit> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val removed = conversation.members.removeIf { it.user_id == userId }
            if (removed) Response.success(Unit)
            else Response.error(
                404,
                """{"error":"User not found in this conversation"}""".toResponseBody("application/json".toMediaType())
            )
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    override suspend fun getConversation(conversationId: Int): Response<GetConversationResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val members = conversation.members.map {
                Member(
                    user_id = it.user_id,
                    role_in_conversation = it.role_in_conversation,
                    status = it.status
                )
            }.toTypedArray()
            Response.success(
                GetConversationResponse(
                    conversation_id = conversation.conversation_id,
                    name = conversation.name,
                    type = conversation.type,
                    module_id = conversation.module_id,
                    visibility = conversation.visibility,
                    max_members = conversation.max_members,
                    member_count = conversation.members.size,
                    members = members,
                    date_created = conversation.date_created
                )
            )
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessage(
        request: SendMessageRequest,
        conversationId: Int
    ): Response<SendMessageResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val id = nextMessageId++
            val now = Instant.now().toString()
            val message = InternalMessage(
                message_id = id,
                sender_id = request.sender_id,
                message_text = request.message_text,
                attachment_url = request.attachment_url,
                attachment_type = request.attachment_type,
                sent_at = now
            )
            conversation.messages.add(message)
            Response.success(
                SendMessageResponse(
                    message_id = id,
                    conversation_id = conversationId,
                    sender_id = request.sender_id,
                    message_text = request.message_text,
                    attachment_url = request.attachment_url,
                    attachment_type = request.attachment_type,
                    sent_at = now
                )
            )
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    override suspend fun deleteMessage(conversationId: Int, messageId: Int): Response<Unit> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val removed = conversation.messages.removeIf { it.message_id == messageId }
            if (removed) Response.success(Unit)
            else Response.error(
                404,
                """{"error":"Message not found"}""".toResponseBody("application/json".toMediaType())
            )
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMessagesInConversation(
        conversationId: Int,
        fromDate: String?,
        toDate: String?,
        limit: Int?
    ): Response<GetMessagesResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val filtered = conversation.messages.filter { msg ->
                val sentAt = Instant.parse(msg.sent_at)
                val fromOk = fromDate?.let { sentAt >= Instant.parse(it) } ?: true
                val toOk = toDate?.let { sentAt <= Instant.parse(it) } ?: true
                fromOk && toOk
            }.sortedBy { it.sent_at }
            val limited = limit?.let { filtered.take(it) } ?: filtered
            val responseMessages = limited.map {
                Message(
                    message_id = it.message_id,
                    sender_id = it.sender_id,
                    message_text = it.message_text,
                    attachment_url = it.attachment_url,
                    sent_at = it.sent_at
                )
            }.toTypedArray()
            Response.success(GetMessagesResponse(messages = responseMessages))
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun joinConversation(
        request: JoinConversationRequest,
        conversationId: Int
    ): Response<JoinConversationResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val existing = conversation.members.find { it.user_id == request.user_id }
            if (existing != null) {
                if (existing.status == "active") {
                    Response.error(
                        400,
                        """{"error":"User is already an active member"}""".toResponseBody("application/json".toMediaType())
                    )
                } else {
                    val now = Instant.now().toString()
                    existing.status = "active"
                    existing.joined_at = now
                    existing.left_at = null
                    Response.success(
                        JoinConversationResponse(
                            conversation_member_id = nextConversationMemberId++,
                            user_id = existing.user_id,
                            role_in_conversation = existing.role_in_conversation,
                            status = existing.status,
                            joined_at = existing.joined_at,
                            left_at = existing.left_at
                        )
                    )
                }
            } else {
                val now = Instant.now().toString()
                val member = InternalMember(user_id = request.user_id, joined_at = now)
                conversation.members.add(member)
                Response.success(
                    JoinConversationResponse(
                        conversation_member_id = nextConversationMemberId++,
                        user_id = member.user_id,
                        role_in_conversation = member.role_in_conversation,
                        status = member.status,
                        joined_at = member.joined_at,
                        left_at = member.left_at
                    )
                )
            }
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun rejoinConversation(
        request: RejoinConversationRequest,
        conversationId: Int
    ): Response<RejoinConversationResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val member = conversation.members.find { it.user_id == request.user_id }
            if (member != null) {
                if (member.status == "active") {
                    Response.error(
                        400,
                        """{"error":"User is already active in the conversation"}""".toResponseBody("application/json".toMediaType())
                    )
                } else {
                    val now = Instant.now().toString()
                    member.status = "active"
                    member.joined_at = now
                    member.left_at = null
                    Response.success(
                        RejoinConversationResponse(
                            conversation_member_id = nextConversationMemberId++,
                            status = member.status,
                            joined_at = member.joined_at
                        )
                    )
                }
            } else {
                Response.error(
                    404,
                    """{"error":"User was never a member"}""".toResponseBody("application/json".toMediaType())
                )
            }
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    override suspend fun changeRole(
        request: ChangeMemberRoleRequest,
        conversationId: Int,
        userId: String
    ): Response<ChangeMemberRoleResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val member = conversation.members.find { it.user_id == userId }
            if (member != null) {
                member.role_in_conversation = request.role_in_conversation
                Response.success(
                    ChangeMemberRoleResponse(
                        conversation_member_id = nextConversationMemberId++,
                        role_in_conversation = member.role_in_conversation
                    )
                )
            } else {
                Response.error(
                    404,
                    """{"error":"Member not found"}""".toResponseBody("application/json".toMediaType())
                )
            }
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }

    override suspend fun getConversationMembers(conversationId: Int): Response<GetConversationMembersResponse> {
        val conversation = conversations.find { it.conversation_id == conversationId }
        return if (conversation != null) {
            val responseMembers = conversation.members.map {
                Member(
                    user_id = it.user_id,
                    role_in_conversation = it.role_in_conversation,
                    status = it.status
                )
            }.toTypedArray()
            Response.success(GetConversationMembersResponse(members = responseMembers))
        } else {
            Response.error(
                404,
                """{"error":"Conversation not found"}""".toResponseBody("application/json".toMediaType())
            )
        }
    }
}
