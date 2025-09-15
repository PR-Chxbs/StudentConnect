package com.prince.studentconnect.remote.api

import com.prince.studentconnect.remote.dto.module.CreateModuleRequest
import com.prince.studentconnect.remote.dto.module.GetModulesResponse
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
    suspend fun getModule(
        @Query("course_id") courseId: Int? = null,
        @Query("lecturer_id") lecturerId: Int? = null
    ): Response<GetModulesResponse>

    @POST("modules")
    suspend fun addModule(
        @Body request: CreateModuleRequest
    )

    @PUT("modules/{module_id}")
    suspend fun updateModule(
        @Body request: CreateModuleRequest,
        @Path("module_id") moduleId: Int
    )

    @DELETE("modules/{module_id}")
    suspend fun deleteModule(
        @Path("module_id") moduleId: Int
    )
}