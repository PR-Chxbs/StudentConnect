package com.prince.studentconnect.data.remote.dto.course

data class UpdateCourseResponse(
    val course_id: Int,
    val name: String,
    val description: String,
    val duration_years: Int,
    val campus: Campus
)
