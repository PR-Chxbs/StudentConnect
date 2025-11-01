package com.prince.studentconnect.data.remote.dto.module

data class DeleteModuleResponse(
    val success: Boolean,
    val message: String,
    val deleted_at: String
)