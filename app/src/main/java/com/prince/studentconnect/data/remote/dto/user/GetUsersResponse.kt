package com.prince.studentconnect.data.remote.dto.user

data class GetUsersResponse(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val role: String,
    val course: Course?,
    val module: List<Module>?
)

data class User(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val role: String,
    val course: Course?,
    val module: List<Module>?
)

data class Module(
    val module_id: Int,
    val name: String
)