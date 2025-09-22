package com.prince.studentconnect.data.remote.websocket

import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import kotlinx.coroutines.flow.Flow

interface ChatWebSocketClient {
    val incomingMessages: Flow<SendMessageResponse>

    fun connect()
    fun disconnect()
    fun simulateMessageEmits()
    suspend fun sendMessage(request: SendMessageRequest)
}

