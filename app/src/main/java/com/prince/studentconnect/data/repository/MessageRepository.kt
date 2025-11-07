package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.MessageApi
import com.prince.studentconnect.data.remote.dto.conversation.*
import retrofit2.Response

class MessageRepository(private val messageApi: MessageApi) {
    suspend fun getMessage(conversationId: Int): Response<List<GetMessagesResponse>> {
        return messageApi.getMessages(conversationId)
    }

    suspend fun sendMessage(sendMessageRequest: SendMessageRequest, conversationId: Int): Response<SendMessageResponse> {
        return messageApi.sendMessage(sendMessageRequest, conversationId)
    }

    suspend fun deleteMessage(conversationId: Int, messageId: Int): Response<Unit> {
        return messageApi.deleteMessage(conversationId, messageId)
    }
}