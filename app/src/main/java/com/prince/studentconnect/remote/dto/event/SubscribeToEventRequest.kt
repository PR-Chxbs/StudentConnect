package com.prince.studentconnect.remote.dto.event

data class SubscribeToEventRequest(
    val event_participation_id: Int,
    val event_id: Int,
    val user_id: String,
    val status: String,
    val custom_reminder_at: String
)
