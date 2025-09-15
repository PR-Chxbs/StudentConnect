package com.prince.studentconnect.data.remote.dto.event

data class GetEventsResponse(
    val events: Array<com.prince.studentconnect.data.remote.dto.event.Event>
)

data class Event(
    val event_id: Int,
    val title: String,
    val start_at: String,
    val conversation_id: Int?,
    val is_subscribed: Boolean,
    val recurrence_rule: String
)
