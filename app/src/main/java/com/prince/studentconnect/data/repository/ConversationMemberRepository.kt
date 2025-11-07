package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.ConversationMemberApi
import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import retrofit2.Response

class ConversationMemberRepository (private val conversationMemberApi: ConversationMemberApi) {
    suspend fun getConversationMember(conversationId: Int): Response<List<GetConversationMembersResponse>> {
        return conversationMemberApi.getConversationMembers(conversationId)
    }

    suspend fun addConversationMember(addConversationMemberRequest: AddConversationMemberRequest, conversationId: Int): Response<AddConversationMemberResponse> {
        return conversationMemberApi.addConversationMember(addConversationMemberRequest, conversationId)
    }

    suspend fun deleteConversationMember(conversationId: Int, userId: String): Response<Unit> {
        return conversationMemberApi.deleteConversationMember(conversationId, userId)
    }
}