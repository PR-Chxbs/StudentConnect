package com.prince.studentconnect.data.remote.dto.campus

data class GetCampusesResponse(
    val campus: Array<com.prince.studentconnect.data.remote.dto.campus.Campus>
)

data class Campus(
    val campus_id: Int,
    val name: String,
    val location: String
)
