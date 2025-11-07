package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import retrofit2.Response
import retrofit2.http.*

interface ConversationMemberApi {
    @GET("conversation_members")
    suspend fun getConversationMembers(): Response<List<GetConversationMembersResponse>>

    @POST("conversation_members")
    suspend fun addConversationMember(
        @Body request: AddConversationMemberRequest
    ): Response<AddConversationMemberResponse>

    /*@PUT("conversation_members/{id}")
    suspend fun updateConversationMember(
        @Path("id") memberId: Int,
        @Body request: UpdateConversationMemberRequest
    ): Response<UpdateConversationMemberResponse>*/

    @DELETE("conversation_members/{id}")
    suspend fun leaveConversationMember(@Path("id") memberId: Int): Response<LeaveConversationMemberResponse>
}