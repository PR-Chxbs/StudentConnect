package com.prince.studentconnect.data.remote.dto.campus

data class GetCampusesResponse(
    val campus_id: Int,
    val name: String,
    val location: String,
    val campus_image_url: String
)

data class Campus(
    val campus_id: Int,
    val name: String,
    val location: String,
    val campus_image_url: String
)
