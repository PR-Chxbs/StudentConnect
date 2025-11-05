package com.prince.studentconnect.data.remote.dto.event

data class GetEventsResponse(
    val event_id: Int,
    val title: String,
    val start_at: String,
    val icon_url: String,
    val color_code: String,
    val conversation_id: Int?,
    val is_subscribed: Boolean,
    val recurrence_rule: String
)

data class Event(
    val event_id: Int,
    val title: String,
    val start_at: String,
    val icon_url: String,
    val color_code: String,
    val conversation_id: Int?,
    val is_subscribed: Boolean,
    val recurrence_rule: String
)
