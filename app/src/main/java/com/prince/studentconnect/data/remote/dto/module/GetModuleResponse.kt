package com.prince.studentconnect.data.remote.dto.module

data class GetModuleResponse(
    val module_id: Int,
    val name: String,
    val code: String,
    val description: String
)