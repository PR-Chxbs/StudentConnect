package com.prince.studentconnect.data.remote.dto.user

data class GetUserResponse(
    val user_id: String,
    val student_number: String?,
    val first_name: String,
    val last_name: String,
    val role: String,
    val bio: String,
    val profile_picture_url: String,
    val campus: Campus,
    val course: Course?
)

data class Campus(
    val campus_id: Int,
    val name: String
)

data class Course(
    val course_id: Int,
    val name: String
)
