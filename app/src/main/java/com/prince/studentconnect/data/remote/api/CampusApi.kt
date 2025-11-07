package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.campus.*
import retrofit2.Response
import retrofit2.http.*

interface CampusApi {
    @GET("campuses")
    suspend fun getCampuses(): Response<List<GetCampusesResponse>>

    @POST("campuses")
    suspend fun addCampus(
        @Body request: AddCampusRequest
    ): Response<AddCampusResponse>

    @PUT("campuses/{campus_id}")
    suspend fun updateCampus(
        @Body request: UpdateCampusRequest,
        @Path("campus_id") campusId: Int
    ): Response<UpdateCampusResponse>

    @DELETE("campuses/{campus_id}")
    suspend fun deleteCampus(
        @Path("campus_id") campusId: Int
    ): Response<DeleteCampusResponse>
}