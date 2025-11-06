package com.prince.studentconnect.data.remote.dto.relationship

data class LinkCourseModuleRequest(
    val course_id: Int,
    val module_id: Int,
    val year_level: Int
)
