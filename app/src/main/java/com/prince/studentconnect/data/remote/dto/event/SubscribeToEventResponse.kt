package com.prince.studentconnect.data.remote.dto.event

data class SubscribeToEventResponse(
    val user_id: String,
    val is_creator: Boolean,
    val status: String,
    val custom_reminder_at: String
)
