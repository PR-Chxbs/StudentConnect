package com.prince.studentconnect.remote.api

import com.prince.studentconnect.remote.dto.campus.AddCampusRequest
import com.prince.studentconnect.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.remote.dto.campus.UpdateCampusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CampusApi {
    @GET("campuses")
    suspend fun getCampuses(): Response<GetCampusesResponse>

    @POST("campuses")
    suspend fun addCampus(
        @Body request: AddCampusRequest
    )

    @PUT("campuses/{campus_id}")
    suspend fun updateCampus(
        @Body request: UpdateCampusRequest,
        @Path("campus_id") campusId: Int
    )

    @DELETE("campuses/{campus_id}")
    suspend fun deleteCampus(
        @Path("campus_id") campusId: Int
    )
}