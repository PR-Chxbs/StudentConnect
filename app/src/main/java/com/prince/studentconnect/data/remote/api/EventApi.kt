package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.event.*
import retrofit2.Response
import retrofit2.http.*

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
    ): Response<UpdateEventResponse>

    @PATCH("events/{event_id}")
    suspend fun patchEvent(
        @Body request: PatchEventRequest,
        @Path("event_id") eventId: Int
    ): Response<Unit>

    @DELETE("events/{event_id}")
    suspend fun deleteEvent(
        @Path("event_id") eventId: Int
    ): Response<Unit>

    @POST("events/{event_id}/participation")
    suspend fun subscribeToEvent(
        @Body request: SubscribeToEventRequest,
        @Path("event_id") eventId: Int
    ): Response<SubscribeToEventResponse>

    @DELETE("events/{event_id}/participation/{user_id}")
    suspend fun unsubscribeFromEvent(
        @Path("event_id") eventId: Int,
        @Path("user_id") userId: String
    ): Response<Unit>

    @GET("users/{user_id}/events")
    suspend fun getUserEvents(
        @Path("user_id") userId: String,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null
    ): Response<List<GetEventsResponse>>

    @GET("conversations/{conversation_id}/events")
    suspend fun getConversationEvents(
        @Path("conversation_id") conversationId: Int,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null
    ): Response<List<GetEventsResponse>>

    @GET("events/{event_id}/participants")
    suspend fun getEventParticipants(
        @Path("event_id") eventId: Int,
    ): Response<List<GetParticipantsResponse>>
}