package com.prince.studentconnect.data.remote.dto.user

data class UpdateUserRequest(
    val first_name: String,
    val last_name: String,
    val bio: String,
    val campus_id: Int,
    val course_id: Int,
    val profile_picture_url: String,
    val student_number: String? // <-- NEW
)
