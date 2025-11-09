package com.prince.studentconnect.data.remote.dto.notification

data class CreateDeviceTokenRequest(
    val user_id: String,
    val device_token: String,
    val platform: String = "Android"
)