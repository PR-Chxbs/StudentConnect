package com.prince.studentconnect.data.remote.api

import androidx.room.Update
import com.prince.studentconnect.data.remote.dto.event.*
import retrofit2.Response
import retrofit2.http.*

interface EventParticipationApi {
    @GET("event_participations")
    suspend fun getEventParticipation(): Response<List<GetEventParticipationResponse>>

    @POST("event_participations")
    suspend fun addEventParticipation(@Path("id") id: Int, @Body request: UpdateEventParticipationRequest): Reponse<UpdateEventParticipationResponse>
}