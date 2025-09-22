package com.prince.studentconnect.data.remote.websocket

import com.prince.studentconnect.data.remote.dto.conversation.SendMessageRequest
import com.prince.studentconnect.data.remote.dto.conversation.SendMessageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.*

class RealChatWebSocketClient(
    private val serverUrl: String
) : ChatWebSocketClient {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _incomingMessages = MutableSharedFlow<SendMessageResponse>()
    override val incomingMessages: SharedFlow<SendMessageResponse> = _incomingMessages

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val messageAdapter = moshi.adapter(SendMessageResponse::class.java)
    private val requestAdapter = moshi.adapter(SendMessageRequest::class.java)

    override fun connect() {
        val request = Request.Builder().url(serverUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("WebSocket connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                scope.launch {
                    try {
                        val message = messageAdapter.fromJson(text)
                        message?.let { _incomingMessages.emit(it) }
                    } catch (e: Exception) {
                        println("WebSocket parse error: ${e.message}")
                    }
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("WebSocket failure: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("WebSocket closed: $code $reason")
            }
        })
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client disconnected")
        webSocket = null
    }

    override suspend fun sendMessage(request: SendMessageRequest) {
        val jsonString = requestAdapter.toJson(request)
        webSocket?.send(jsonString)
    }
}
