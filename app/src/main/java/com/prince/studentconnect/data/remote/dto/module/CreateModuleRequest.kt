package com.prince.studentconnect.data.remote.dto.module

data class CreateModuleRequest(
    val name: String,
    val code: String,
    val description: String
)
