package com.prince.studentconnect.remote.dto.course

data class UpdateCourseRequest(
    val name: String,
    val description: String,
    val duration_years: Int,
    val campus_id: Int
)
