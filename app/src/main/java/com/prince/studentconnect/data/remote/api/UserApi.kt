package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.user.GetUserResponse
import com.prince.studentconnect.data.remote.dto.user.GetUsersResponse
import com.prince.studentconnect.data.remote.dto.user.UpdateUserRequest
import com.prince.studentconnect.data.remote.dto.user.UpdateUserRoleRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("users/{user_id}")
    suspend fun getUser(
        @Path("user_id") userId: String
    ): Response<GetUserResponse>

    @GET("users")
    suspend fun getUsers(
        @Query("role") role: String? = null,
        @Query("campus_id") campusId: Int? = null,
        @Query("course_id") courseId: Int? = null
    ): Response<GetUsersResponse>

    @PUT("users/{user_id}")
    suspend fun updateUser(
        @Body request: UpdateUserRequest,
        @Path("user_id") userId: String
    ): Response<Unit>

    @DELETE("users/{user_id}")
    suspend fun deleteUser(
        @Path("user_id") userId: String
    ): Response<Unit>

    @PATCH("users/{user_id}/role")
    suspend fun updateUserRole(
        @Body request: UpdateUserRoleRequest,
        @Path("user_id") userId: String
    ): Response<Unit>
}