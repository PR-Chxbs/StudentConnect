package com.prince.studentconnect.data.remote.dto.module

data class GetModulesResponse(
    val modules: Array<Module>
)

data class Module(
    val module_id: Int,
    val name: String,
    val code: String
)