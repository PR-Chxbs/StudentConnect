package com.prince.studentconnect.data.remote.websocket

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
        // f
    }

    override fun disconnect() {
        // Nothing needed for fake
    }

    // Format Instant to ISO 8601 with UTC 'Z'
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ISO_INSTANT

    @RequiresApi(Build.VERSION_CODES.O)
    override fun simulateMessageEmits() {
        // Simulate server sending messages periodically
        val message = listOf(
            listOf("Yo Prince", "Bruh...", "I need you to start focusing", "We have a submission soon", "Call me ASAP"),
            listOf("Uhm", "John is blowing up my phone", "Talking about some assignment", "What assignment is my problem", "I should stop missing class")
        )

        scope.launch {
            repeat(2){ x ->
                repeat(5) { i ->
                    delay(3000L) // every 3 seconds
                    val fakeMessage = SendMessageResponse(
                        message_id = i,
                        conversation_id = x+1,
                        sender_id = "student_$i",
                        message_text = message[x][i],
                        attachment_url = null,
                        attachment_type = null,
                        sent_at = formatter.format(Instant.ofEpochMilli(System.currentTimeMillis()))
                    )
                    // Convert to JSON and back to simulate network parsing
                    val json = messageAdapter.toJson(fakeMessage)
                    // val newLog = "New message emitted ($i): $json"
                    messageAdapter.fromJson(json)?.let { _incomingMessages.emit(it) }
                }
            }
        }

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
