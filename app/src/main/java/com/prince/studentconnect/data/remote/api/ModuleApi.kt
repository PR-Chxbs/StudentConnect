package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.module.*
import retrofit2.Response
import retrofit2.http.*

interface ModuleApi {
    @GET("modules")
    suspend fun getModules(
        @Query("course_id") courseId: Int? = null,
        @Query("lecturer_id") lecturerId: String? = null
    ): Response<List<GetModulesResponse>>

    @GET("modules/{module_id}")
    suspend fun getModule(
        @Path("module_id") moduleId: Int
    ): Response<Unit>

    @POST("modules")
    suspend fun addModule(
        @Body request: CreateModuleRequest
    ): Response<CreateModuleResponse>

    @PUT("modules/{module_id}")
    suspend fun updateModule(
        @Body request: UpdateModuleRequest,
        @Path("module_id") moduleId: Int
    ): Response<UpdateModuleResponse>

    @DELETE("modules/{module_id}")
    suspend fun deleteModule(
        @Path("module_id") moduleId: Int
    ): Response<DeleteModuleResponse>
}