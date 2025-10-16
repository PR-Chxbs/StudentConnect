package com.prince.studentconnect.data.remote.dto.auth

data class GetIdentityResponse(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,

)
