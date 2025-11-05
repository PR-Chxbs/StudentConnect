package com.prince.studentconnect.data.remote.dto.event

data class SubscribeToEventRequest(
    val user_id: String,
    val status: String,
    val reminder_at: String?
)
