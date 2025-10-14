package com.prince.studentconnect.data.remote.dto.auth

data class VerifyTokenResponse(
    val user_id: String,
    val email: String,
    val role: String,
    val first_name: String,
    val last_name: String,
    val campus_id: Int,
    val course_id: Int
)
