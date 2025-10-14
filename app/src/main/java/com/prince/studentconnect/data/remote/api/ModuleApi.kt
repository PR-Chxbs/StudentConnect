package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.module.CreateModuleRequest
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse
import com.prince.studentconnect.data.remote.dto.module.UpdateModuleRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ModuleApi {
    @GET("modules")
    suspend fun getModules(
        @Query("course_id") courseId: Int? = null,
        @Query("lecturer_id") lecturerId: String? = null
    ): Response<GetModulesResponse>

    @GET("modules/{module_id}")
    suspend fun getModule(
        @Path("module_id") moduleId: Int
    ): Response<Unit>

    @POST("modules")
    suspend fun addModule(
        @Body request: CreateModuleRequest
    ): Response<Unit>

    @PUT("modules/{module_id}")
    suspend fun updateModule(
        @Body request: UpdateModuleRequest,
        @Path("module_id") moduleId: Int
    ): Response<Unit>

    @DELETE("modules/{module_id}")
    suspend fun deleteModule(
        @Path("module_id") moduleId: Int
    ): Response<Unit>
}