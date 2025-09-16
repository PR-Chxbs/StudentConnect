package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.UserApi
import com.prince.studentconnect.data.remote.dto.user.*
import retrofit2.Response

class UserRepository(
    private val userApi: UserApi
) {
    suspend fun getUser(userId: String): Response<GetUserResponse> {
        return userApi.getUser(userId)
    }

    suspend fun getUsers(role: String?, campus_id: Int?, course_id: Int?): Response<GetUsersResponse> {
        return userApi.getUsers(role, campus_id, course_id)
    }

    suspend fun updateUser(updateUserRequest: UpdateUserRequest, userId: String): Response<Any> {
        return userApi.updateUser(updateUserRequest, userId)
    }

    suspend fun deleteUser(userId: String): Response<Any> {
        return userApi.deleteUser(userId)
    }

    suspend fun updateUserRole(updateUserRoleRequest: UpdateUserRoleRequest, userId: String): Response<Any> {
        return userApi.updateUserRole(updateUserRoleRequest, userId)
    }
}