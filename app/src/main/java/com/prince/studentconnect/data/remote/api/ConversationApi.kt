package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.conversation_membership.ChangeMemberRoleRequest
import com.prince.studentconnect.data.remote.dto.conversation_membership.ChangeMemberRoleResponse
import com.prince.studentconnect.data.remote.dto.conversation_membership.GetConversationMembersResponse
import com.prince.studentconnect.data.remote.dto.conversation_membership.JoinConversationRequest
import com.prince.studentconnect.data.remote.dto.conversation_membership.JoinConversationResponse
import com.prince.studentconnect.data.remote.dto.conversation_membership.RejoinConversationRequest
import com.prince.studentconnect.data.remote.dto.conversation_membership.RejoinConversationResponse
import com.prince.studentconnect.data.remote.dto.conversation.AddConversationMemberRequest
import com.prince.studentconnect.data.remote.dto.conversation.AddConversationMemberResponse
import com.prince.studentconnect.data.remote.dto.conversation.Conversation
import com.prince.studentconnect.data.remote.dto.conversation.CreateConversationRequest
import com.prince.studentconnect.data.remote.dto.conversation.CreateConversationResponse
import com.prince.studentconnect.data.remote.dto.conversation.GetConversationResponse
import com.prince.studentconnect.data.remote.dto.conversation.GetConversationsResponse
import com.prince.studentconnect.data.remote.dto.conversation.GetMessagesResponse
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConversationApi {
    @POST("conversations")
    suspend fun createConversation(
        @Body request: CreateConversationRequest
    ): Response<CreateConversationResponse>

    @POST("conversations/{conversation_id}/members")
    suspend fun addConversationMember(
        @Body request: AddConversationMemberRequest,
        @Path("conversation_id") conversationId: Int
    ): Response<AddConversationMemberResponse>

    @DELETE("conversations/{conversation_id}/members/{user_id}")
    suspend fun removeConversationMember(
        @Path("conversation_id") conversationId: Int,
        @Path("user_id") userId: String
    ): Response<Unit>

    @GET("conversations/{conversation_id}")
    suspend fun getConversation(
        @Path("conversation_id") conversationId: Int,
    ): Response<GetConversationResponse>

    @GET("conversations")
    suspend fun getConversations(
        @Query("userId") userId: String,
        @Query("search") search: String? = null,
        @Query("type") type: String? = null,
        @Query("campus_id") campusId: Int? = null
    ): Response<List<Conversation>>

    @POST("conversations/{conversation_id}/messages")
    suspend fun sendMessage(
        @Body request: SendMessageRequest,
        @Path("conversation_id") conversationId: Int
    ): Response<SendMessageResponse>

    @DELETE("conversations/{conversation_id}/messages/{message_id}")
    suspend fun deleteMessage(
        @Path("conversation_id") conversationId: Int,
        @Path("message_id") messageId: Int
    ): Response<Unit>

    @GET("conversations/{conversation_id}/messages")
    suspend fun getMessagesInConversation(
        @Path("conversation_id") conversationId: Int,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("limit") limit: Int? = null
    ): Response<GetMessagesResponse>

    // ----------- Conversation membership -----------

    @POST("conversations/{conversation_id}/members/join")
    suspend fun joinConversation(
        @Body request: JoinConversationRequest,
        @Path("conversation_id") conversationId: Int
    ): Response<JoinConversationResponse>

    @POST("conversations/{conversation_id}/members/rejoin")
    suspend fun rejoinConversation(
        @Body request: RejoinConversationRequest,
        @Path("conversation_id") conversationId: Int
    ): Response<RejoinConversationResponse>

    @PATCH("conversations/{conversation_id}/members/{user_id}")
    suspend fun changeRole(
        @Body request: ChangeMemberRoleRequest,
        @Path("conversation_id") conversationId: Int,
        @Path("user_id") userId: String
    ): Response<ChangeMemberRoleResponse>

    @GET("conversations/{conversation_id}/members")
    suspend fun getConversationMembers(
        @Path("conversation_id") conversationId: Int
    ): Response<GetConversationMembersResponse>
}