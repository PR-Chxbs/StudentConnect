package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.conversation.*
import com.prince.studentconnect.data.remote.dto.conversation_membership.*
import retrofit2.Response
import retrofit2.http.*

interface MessageApi {
    @GET("messages")
    suspend fun getMessage(): Response<List<GetMessagesResponse>>

    @POST("messages")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<SendMessageResponse>

}