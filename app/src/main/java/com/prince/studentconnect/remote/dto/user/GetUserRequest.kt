package com.prince.studentconnect.remote.dto.user

data class GetUserRequest(
    val user_id: String, // UUID from Firebase
)
