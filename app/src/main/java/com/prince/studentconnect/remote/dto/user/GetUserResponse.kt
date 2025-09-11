package com.prince.studentconnect.remote.dto.user

data class GetUserResponse(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val role: String,
    val bio: String,
    val campus: Campus,
    val course: Course,
    val profile_picture_url: String
)

data class Campus(
    val campus_id: Int,
    val name: String
)

data class Course(
    val course_id: Int,
    val name: String
)
