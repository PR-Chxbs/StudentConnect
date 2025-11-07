package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.local.Dao.ConversationDao
import com.prince.studentconnect.data.local.Dao.MessageDao
import com.prince.studentconnect.data.local.Entities.Conversation
import com.prince.studentconnect.data.local.Entities.Message
import com.prince.studentconnect.data.remote.api.ConversationApi
import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import com.prince.studentconnect.data.remote.websocket.ChatWebSocketClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class ConversationRepository(
    private val conversationApi: ConversationApi,
    private val webSocketClient: ChatWebSocketClient,
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao
) {
    // WebSocket flow for real-time incoming messages
    val incomingMessages: Flow<SendMessageResponse> = webSocketClient.incomingMessages

    // conversation APIs

    suspend fun createConversation(request: CreateConversationRequest): Response<CreateConversationResponse> {
        val response = conversationApi.createConversation(request)
        if (response.isSuccessful) {
            response.body()?.let { dto ->
                val convo = Conversation(
                    conversation_id = dto.conversation_id,
                    name = dto.name,
                    type = dto.type,
                    createdBy = dto.created_by,
                    createdAt = dto.created_at
                )
                withContext(Dispatchers.IO) {
                    conversationDao.insertConversation(convo)
                }
            }
        }
        return response
    }

    suspend fun getConversation(conversationId: Int): Conversation? {
        return try {
            val response = conversationApi.getConversation(conversationId)
            if (response.isSuccessful) {
                response.body()?.let { dto ->
                    val convo = Conversation(
                        conversation_id = dto.conversation_id,
                        name = dto.name,
                        type = dto.type,
                        createdBy = dto.created_by,
                        createdAt = dto.created_at
                    )
                    withContext(Dispatchers.IO) {
                        conversationDao.updateConversation(convo)
                    }
                    convo
                }
            } else {
                withContext(Dispatchers.IO) {
                    conversationDao.getConversationById(conversationId)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) {
                conversationDao.getConversationById(conversationId)
            }
        }
    }

    suspend fun getConversations(
        userId: String,
        search: String? = null,
        type: String? = null,
        campusId: Int? = null
    ): List<Conversation> {
        return try {
            val response = conversationApi.getConversations(userId, search, type, campusId)
            if (response.isSuccessful) {
                val conversationsFromApi = response.body()?.map { dto ->
                    Conversation(
                        conversation_id = dto.conversation_id,
                        name = dto.name,
                        type = dto.type,
                        createdBy = dto.created_by,
                        createdAt = dto.created_at
                    )
                } ?: emptyList()

                withContext(Dispatchers.IO) {
                    conversationDao.insertConversations(conversationsFromApi)
                }

                conversationsFromApi
            } else {
                withContext(Dispatchers.IO) { conversationDao.getAllConversations() }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) { conversationDao.getAllConversations() }
        }
    }

    suspend fun addConversationMember(
        request: AddConversationMemberRequest,
        conversationId: Int
    ): Response<AddConversationMemberResponse> {
        return conversationApi.addConversationMember(request, conversationId)
    }

    suspend fun removeConversationMember(conversationId: Int, userId: String): Response<Unit> {
        return conversationApi.removeConversationMember(conversationId, userId)
    }

    // messages
    suspend fun sendMessage(request: SendMessageRequest, conversationId: Int): Response<SendMessageResponse> {
        val response = conversationApi.sendMessage(request, conversationId)
        if (response.isSuccessful) {
            response.body()?.let { dto ->
                val message = Message(
                    message_id = dto.message_id,
                    conversation_id = dto.conversation_id,
                    sender_id = dto.sender_id,
                    content = dto.content,
                    sent_at = dto.sent_at
                )
                withContext(Dispatchers.IO) {
                    messageDao.insertMessage(message)
                }
            }
        }
        return response
    }

    suspend fun getMessagesInConversation(
        conversationId: Int,
        fromDate: String? = null,
        toDate: String? = null,
        limit: Int? = null
    ): List<Message> {
        return try {
            val response = conversationApi.getMessagesInConversation(conversationId, fromDate, toDate, limit)
            if (response.isSuccessful) {
                val messages = response.body()?.map { dto ->
                    Message(
                        message_id = dto.message_id,
                        conversation_id = dto.conversation_id,
                        sender_id = dto.sender_id,
                        content = dto.content,
                        sent_at = dto.sent_at
                    )
                } ?: emptyList()

                withContext(Dispatchers.IO) {
                    messageDao.insertMessages(messages)
                }

                messages
            } else {
                withContext(Dispatchers.IO) { messageDao.getMessagesByConversationId(conversationId) }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.IO) { messageDao.getMessagesByConversationId(conversationId) }
        }
    }

    suspend fun deleteMessage(conversationId: Int, messageId: Int): Response<Unit> {
        val response = conversationApi.deleteMessage(conversationId, messageId)
        if (response.isSuccessful) {
            withContext(Dispatchers.IO) {
                messageDao.deleteMessageById(messageId)
            }
        }
        return response
    }

    // WebSocket
    suspend fun sendMessageViaWebSocket(request: SendMessageWebSocketJson) {
        webSocketClient.sendMessage(request)
    }

    fun connect() = webSocketClient.connect()
    fun disconnect() = webSocketClient.disconnect()
    fun simulateMessageEmits() = webSocketClient.simulateMessageEmits()

    // conversation membership
    suspend fun joinConversation(request: JoinConversationRequest, conversationId: Int): Response<JoinConversationResponse> {
        return conversationApi.joinConversation(request, conversationId)
    }

    suspend fun rejoinConversation(request: RejoinConversationRequest, conversationId: Int): Response<RejoinConversationResponse> {
        return conversationApi.rejoinConversation(request, conversationId)
    }

    suspend fun changeRole(
        request: ChangeMemberRoleRequest,
        conversationId: Int,
        userId: String
    ): Response<ChangeMemberRoleResponse> {
        return conversationApi.changeRole(request, conversationId, userId)
    }

    suspend fun getConversationMembers(conversationId: Int): Response<List<GetConversationMembersResponse>> {
        return conversationApi.getConversationMembers(conversationId)
    }
}
