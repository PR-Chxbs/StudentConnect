package com.prince.studentconnect.data.remote.dto.event

data class SubscribeToEventResponse(
    val event_participation_id: Int,
    val event_id: Int,
    val user_id: String,
    val status: String
)
