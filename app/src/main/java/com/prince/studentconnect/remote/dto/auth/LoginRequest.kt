package com.prince.studentconnect.remote.dto.auth

data class LoginRequest(
    val email: String,
    val password: String
)
