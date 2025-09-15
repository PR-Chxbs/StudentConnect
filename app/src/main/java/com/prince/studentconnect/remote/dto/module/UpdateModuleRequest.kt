package com.prince.studentconnect.remote.dto.module

data class UpdateModuleRequest(
    val name: String,
    val code: String,
    val description: String
)
