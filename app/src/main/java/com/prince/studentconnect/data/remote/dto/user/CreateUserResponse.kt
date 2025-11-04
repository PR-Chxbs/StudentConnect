package com.prince.studentconnect.data.remote.dto.user

data class CreateUserResponse(
    val user_id: String,
    val student_number: String?,
    val first_name: String,
    val last_name: String,
    val email: String,
    val role: String,
    val bio: String,
    val profile_picture_url: String,
    val campus: Campus?,
    val course: Course?
)
