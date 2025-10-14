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

    suspend fun getUsers(role: String?, campusId: Int?, courseId: Int?): Response<GetUsersResponse> {
        return userApi.getUsers(role, campusId, courseId)
    }

    suspend fun updateUser(updateUserRequest: UpdateUserRequest, userId: String): Response<Unit> {
        return userApi.updateUser(updateUserRequest, userId)
    }

    suspend fun deleteUser(userId: String): Response<Unit> {
        return userApi.deleteUser(userId)
    }

    suspend fun updateUserRole(updateUserRoleRequest: UpdateUserRoleRequest, userId: String): Response<Unit> {
        return userApi.updateUserRole(updateUserRoleRequest, userId)
    }
}