package com.prince.studentconnect.data.remote.dto.user

data class GetUsersRequest(
    val role: String?,
    val campus_id: Int?,
    val course_id: Int?
)
