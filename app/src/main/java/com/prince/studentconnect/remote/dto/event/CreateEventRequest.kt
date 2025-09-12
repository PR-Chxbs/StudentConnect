package com.prince.studentconnect.remote.dto.event

data class CreateEventRequest(
    val creator_id: String,
    val conversation_id: Int?, // Nullable for personal
    val title: String,
    val description: String,
    val start_at: String,
    val icon_url: String,
    val color_code: String,
    val recurrence_rule: String,
    val reminder_at: String
)
