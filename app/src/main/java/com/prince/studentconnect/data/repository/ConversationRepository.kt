package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.ConversationApi
import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import com.prince.studentconnect.data.remote.websocket.ChatWebSocketClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ConversationRepository(
    private val conversationApi: ConversationApi,
    private val webSocketClient: ChatWebSocketClient
) {
    val incomingMessages: Flow<SendMessageResponse> = webSocketClient.incomingMessages

    suspend fun createConversation(createConversationRequest: CreateConversationRequest): Response<CreateConversationResponse> {
        return conversationApi.createConversation(createConversationRequest)
    }

    suspend fun addConversationMember(addConversationMemberRequest: AddConversationMemberRequest, conversationId: Int): Response<AddConversationMemberResponse> {
        return conversationApi.addConversationMember(addConversationMemberRequest, conversationId)
    }

    suspend fun removeConversationMember(conversationId: Int, userId: String): Response<Unit> {
        return conversationApi.removeConversationMember(conversationId, userId)
    }

    suspend fun getConversation(conversationId: Int): Response<GetConversationResponse> {
        return conversationApi.getConversation(conversationId)
    }

    suspend fun getConversations(userId: String, search: String? = null, type: String? = null, campusId: Int? = null): Response<List<GetConversationsResponse>> {
        return conversationApi.getConversations(userId, search, type, campusId)
    }

    suspend fun sendMessage(sendMessageRequest: SendMessageRequest, conversationId: Int): Response<SendMessageResponse> {
        return conversationApi.sendMessage(sendMessageRequest, conversationId)
    }

    suspend fun deleteMessage(conversationId: Int, messageId: Int): Response<Unit> {
        return conversationApi.deleteMessage(conversationId, messageId)
    }

    suspend fun getMessagesInConversation(conversationId: Int, fromDate: String? = null, toDate: String? = null, limit: Int? = null): Response<List<GetMessagesResponse>> {
        return conversationApi.getMessagesInConversation(conversationId, fromDate, toDate, limit)
    }

    // ----------- WebSocket -----------

    suspend fun sendMessageViaWebSocket(request: SendMessageWebSocketJson) {
        webSocketClient.sendMessage(request)
    }

    fun connect() = webSocketClient.connect()
    fun disconnect() = webSocketClient.disconnect()
    fun simulateMessageEmits() = webSocketClient.simulateMessageEmits()

    // ----------- Conversation membership -----------

    suspend fun joinConversation(joinConversationRequest: JoinConversationRequest, conversationId: Int): Response<JoinConversationResponse> {
        return conversationApi.joinConversation(joinConversationRequest, conversationId)
    }

    suspend fun rejoinConversation(rejoinConversationRequest: RejoinConversationRequest, conversationId: Int): Response<RejoinConversationResponse> {
        return conversationApi.rejoinConversation(rejoinConversationRequest, conversationId)
    }

    suspend fun changeRole(changeMemberRoleRequest: ChangeMemberRoleRequest, conversationId: Int, userId: String): Response<ChangeMemberRoleResponse> {
        return conversationApi.changeRole(changeMemberRoleRequest, conversationId, userId)
    }

    suspend fun getConversationMembers(conversationId: Int): Response<List<GetConversationMembersResponse>> {
        return conversationApi.getConversationMembers(conversationId)
    }
}