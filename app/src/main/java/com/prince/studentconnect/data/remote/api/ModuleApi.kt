package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.module.CreateModuleRequest
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse
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
    ): Response<com.prince.studentconnect.data.remote.dto.module.GetModulesResponse>

    @POST("modules")
    suspend fun addModule(
        @Body request: com.prince.studentconnect.data.remote.dto.module.CreateModuleRequest
    )

    @PUT("modules/{module_id}")
    suspend fun updateModule(
        @Body request: com.prince.studentconnect.data.remote.dto.module.CreateModuleRequest,
        @Path("module_id") moduleId: Int
    )

    @DELETE("modules/{module_id}")
    suspend fun deleteModule(
        @Path("module_id") moduleId: Int
    )
}