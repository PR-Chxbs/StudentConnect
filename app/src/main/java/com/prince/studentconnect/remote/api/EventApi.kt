package com.prince.studentconnect.remote.api

import com.prince.studentconnect.remote.dto.event.CreateEventRequest
import com.prince.studentconnect.remote.dto.event.CreateEventResponse
import com.prince.studentconnect.remote.dto.event.GetAnEventResponse
import com.prince.studentconnect.remote.dto.event.GetEventsResponse
import com.prince.studentconnect.remote.dto.event.GetParticipantsResponse
import com.prince.studentconnect.remote.dto.event.SubscribeToEventRequest
import com.prince.studentconnect.remote.dto.event.SubscribeToEventResponse
import com.prince.studentconnect.remote.dto.event.UpdateEventRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @POST("events")
    suspend fun createEvent(
        @Body request: CreateEventRequest
    ): Response<CreateEventResponse>

    @GET("events/{event_id}")
    suspend fun getEvent(
        @Path("event_id") eventId: Int
    ): Response<GetAnEventResponse>

    @PUT("events/{event_id}")
    suspend fun updateEvent(
        @Body request: UpdateEventRequest,
        @Path("event_id") eventId: Int
    ): Response<Any>

    @PATCH("events/{event_id}")
    suspend fun patchEvent(
        @Body request: UpdateEventRequest,
        @Path("event_id") eventId: Int
    ): Response<Any>

    @DELETE("events/{event_id}")
    suspend fun deleteEvent(
        @Path("event_id") event_id: Int
    ): Response<Any>

    @POST("events/{event_id}/participation")
    suspend fun subscribeToEvent(
        @Body request: SubscribeToEventRequest,
        @Path("event_id") eventId: Int
    ): Response<SubscribeToEventResponse>

    @DELETE("events/{event_id}/participation")
    suspend fun unsubscribeFromEvent(
        @Path("event_id") event_id: Int
    ): Response<Any>

    @GET("users/{user_id}/events")
    suspend fun getUserEvents(
        @Path("user_id") userId: String,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null
    ): Response<GetEventsResponse>

    @GET("conversation_id/{conversation_id}/events")
    suspend fun getConversationEvents(
        @Path("conversation_id") conversation_id: String,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null
    ): Response<GetEventsResponse>

    @GET("events/{event_id}/participants")
    suspend fun getEventParticipants(
        @Path("event_id") eventId: Int,
    ): Response<GetParticipantsResponse>
}