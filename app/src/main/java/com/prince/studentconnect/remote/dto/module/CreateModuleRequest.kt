package com.prince.studentconnect.remote.dto.module

data class CreateUpdateModuleRequest(
    val name: String,
    val code: String,
    val description: String
)
