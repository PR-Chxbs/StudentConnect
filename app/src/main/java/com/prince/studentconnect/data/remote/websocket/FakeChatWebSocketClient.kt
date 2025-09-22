package com.prince.studentconnect.data.remote.websocket

import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FakeChatWebSocketClient : ChatWebSocketClient {
    private val _incomingMessages = MutableSharedFlow<SendMessageResponse>()
    override val incomingMessages: Flow<SendMessageResponse> = _incomingMessages.asSharedFlow()

    override fun connect() {
        // Simulate a new incoming message 2s after connection
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            _incomingMessages.emit(
                SendMessageResponse(
                    message_id = 1,
                    conversation_id = 1,
                    sender_id = "system",
                    message_text = "Welcome to Fake Chat 🎉",
                    attachment_url = null,
                    attachment_type = null,
                    sent_at = System.currentTimeMillis().toString()
                )
            )
        }
    }

    override fun disconnect() {
        // no-op
    }

    override suspend fun sendMessage(request: SendMessageRequest) {
        val fakeResponse = SendMessageResponse(
            message_id = (1..1000).random(),
            conversation_id = 1,
            sender_id = request.sender_id,
            message_text = request.message_text,
            attachment_url = request.attachment_url,
            attachment_type = request.attachment_type,
            sent_at = System.currentTimeMillis().toString()
        )
        // Emit immediately to simulate echo from server
        _incomingMessages.emit(fakeResponse)
    }
}
/*
private val scope = CoroutineScope(Dispatchers.Default)
override fun connect() {
    // Fake: simulate receiving messages periodically
    scope.launch {
        repeat(5) { i ->
            delay(3000L) // every 3 seconds
            _incomingMessages.emit(
                SendMessageResponse(
                    message_id = i,
                    conversation_id = 1,
                    sender_id = "user_$i",
                    message_text = "Fake message $i",
                    attachment_url = null,
                    attachment_type = null,
                    sent_at = System.currentTimeMillis().toString()
                )
            )
        }
    }
}*/
