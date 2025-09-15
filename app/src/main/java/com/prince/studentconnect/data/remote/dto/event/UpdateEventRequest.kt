package com.prince.studentconnect.data.remote.dto.event

data class UpdateEventRequest(
    val creator_id: String,
    val conversation_id: Int,
    val title: String,
    val description: String,
    val start_at: String,
    val recurrence_rule: String,
    val reminder_at: String,
    val created_at: String,
    val participants: Array<String>
)