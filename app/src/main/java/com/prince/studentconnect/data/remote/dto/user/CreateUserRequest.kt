package com.prince.studentconnect.data.remote.dto.user

data class CreateUserRequest(
    val user_id: String,
    val first_name: String,
    val middle_name: String?,
    val last_name: String,
    val student_number: String?,
    val email: String,
    val phone_number: String,
    val role: String,
    val bio: String,
    val campus_id: Int?,
    val course_id: Int?,
    val profile_picture_url: String,
)