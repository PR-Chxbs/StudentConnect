package com.prince.studentconnect.data.remote.dto.event

data class GetAnEventResponse(
    val event_id: Int,
    val creator_id: String,
    val creator_name: String,
    val conversation_id: Int?,
    val title: String,
    val description: String,
    val start_at: String,
    val icon_url: String,
    val color_code: String,
    val recurrence_rule: String,
    val reminder_at: String,
    val created_at: String,
    val participants: Array<Participant>
)

data class Participant(
    val user_id: String,
    val full_name: String,
    val status: String,
    val is_creator: Boolean,
    val student_number: String = ""
)
