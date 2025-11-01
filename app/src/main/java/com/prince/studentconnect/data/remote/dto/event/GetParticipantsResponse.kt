package com.prince.studentconnect.data.remote.dto.event

data class GetParticipantsResponse(
    val user_id: String,
    val full_name: String,
    val student_number: String?,
    val is_subscribed: Boolean
)
