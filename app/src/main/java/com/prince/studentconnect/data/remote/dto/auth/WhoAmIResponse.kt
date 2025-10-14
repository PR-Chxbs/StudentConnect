package com.prince.studentconnect.data.remote.dto.auth

data class WhoAmIResponse(
    val user_id: String,
    val email: String,
    val role: String,
    val profile_complete: Boolean
)