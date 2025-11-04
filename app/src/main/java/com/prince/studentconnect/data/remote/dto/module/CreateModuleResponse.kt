package com.prince.studentconnect.data.remote.dto.module

data class CreateModuleResponse(
    val success: Boolean,
    val module: CreateModuleResponseModule
)

data class CreateModuleResponseModule (
    val module_id: Int,
    val name: String,
    val code: String,
    val description: String,
    val is_active: Boolean
)
