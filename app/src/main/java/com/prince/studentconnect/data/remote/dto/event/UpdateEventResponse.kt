package com.prince.studentconnect.data.remote.dto.event

data class UpdateEventResponse(
    val event_id: Int,
    val creator_id: String,
    val conversation_id: Int,
    val title: String,
    val description: String,
    val start_at: String,
    val recurrence_rule: String,
    val reminder_at: String,
    val created_at: String,
    val event_participation: List<Participation>,
    val participants: List<UpdateEventResponseParticipant>
)

data class Participation (
    val status: String,
    val user_id: String,
    val is_creator: Boolean
)

data class UpdateEventResponseParticipant (
    val user_id: String,
    val status: String,
    val is_creator: Boolean
)