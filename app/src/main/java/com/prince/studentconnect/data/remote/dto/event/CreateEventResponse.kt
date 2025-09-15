package com.prince.studentconnect.data.remote.dto.event

data class CreateEventResponse(
    val event_id: Int,
    val creator_id: String,
    val conversation_id: Int?,
    val start_at: String,
    val recurrence_rule: String,
    val reminder_at: String,
    val created_at: String
)
