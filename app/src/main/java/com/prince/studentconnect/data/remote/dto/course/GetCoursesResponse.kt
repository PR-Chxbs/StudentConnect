package com.prince.studentconnect.data.remote.dto.course

data class GetCoursesResponse(
    val courses: Array<com.prince.studentconnect.data.remote.dto.course.Course>
)

data class Course(
    val course_id: Int,
    val name: String,
    val description: String,
    val duration_years: Int,
    val campus: com.prince.studentconnect.data.remote.dto.course.Campus
)

data class Campus(
    val campus_id: Int,
    val name: String
)