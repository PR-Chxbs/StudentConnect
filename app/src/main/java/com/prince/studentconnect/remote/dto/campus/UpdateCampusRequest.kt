package com.prince.studentconnect.remote.dto.campus

data class UpdateCampusRequest(
    val name: String,
    val location: String,
    val campus_image_url: String
)
