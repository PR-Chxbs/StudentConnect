package com.prince.studentconnect.data.remote.websocket

import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FakeChatWebSocketClient : ChatWebSocketClient {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val _incomingMessages = MutableSharedFlow<SendMessageResponse>()
    override val incomingMessages: Flow<SendMessageResponse> = _incomingMessages

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val messageAdapter = moshi.adapter(SendMessageResponse::class.java)
    private val requestAdapter = moshi.adapter(SendMessageRequest::class.java)

    override fun connect() {
        // Simulate server sending messages periodically
        scope.launch {
            repeat(5) { i ->
                delay(3000L) // every 3 seconds
                val fakeMessage = SendMessageResponse(
                    message_id = i,
                    conversation_id = 1,
                    sender_id = "user_$i",
                    message_text = "Fake message $i",
                    attachment_url = null,
                    attachment_type = null,
                    sent_at = System.currentTimeMillis().toString()
                )
                // Convert to JSON and back to simulate network parsing
                val json = messageAdapter.toJson(fakeMessage)
                messageAdapter.fromJson(json)?.let { _incomingMessages.emit(it) }
            }
        }
    }

    override fun disconnect() {
        // Nothing needed for fake
    }

    override suspend fun sendMessage(request: SendMessageRequest) {
        // Simulate echo back as if server processed it
        val json = requestAdapter.toJson(request)
        val message = SendMessageResponse(
            message_id = 999,
            conversation_id = 1,
            sender_id = request.sender_id,
            message_text = request.message_text,
            attachment_url = request.attachment_url,
            attachment_type = request.attachment_type,
            sent_at = System.currentTimeMillis().toString()
        )
        val jsonMessage = messageAdapter.toJson(message)
        messageAdapter.fromJson(jsonMessage)?.let { _incomingMessages.emit(it) }
    }
}
