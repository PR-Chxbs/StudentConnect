package com.prince.studentconnect.data.repository

import android.util.Log
import com.prince.studentconnect.data.remote.api.EventApi
import com.prince.studentconnect.data.remote.dto.event.*
import retrofit2.Response

class EventRepository(
    private val eventApi: EventApi
) {
    suspend fun createEvent(createEventRequest: CreateEventRequest): Response<CreateEventResponse> {
        return eventApi.createEvent(createEventRequest)
    }

    suspend fun getEvent(eventId: Int): Response<GetAnEventResponse> {
        return eventApi.getEvent(eventId)
    }

    suspend fun updateEvent(updateEventRequest: UpdateEventRequest, eventId: Int): Response<UpdateEventResponse> {
        return eventApi.updateEvent(updateEventRequest, eventId)
    }

    suspend fun patchEvent(patchEventRequest: PatchEventRequest, eventId: Int): Response<Unit> {
        return eventApi.patchEvent(patchEventRequest, eventId)
    }

    suspend fun deleteEvent(eventId: Int): Response<Unit> {
        return eventApi.deleteEvent(eventId)
    }

    suspend fun subscribeToEvent(subscribeToEventRequest: SubscribeToEventRequest, eventId: Int): Response<SubscribeToEventResponse> {
        return eventApi.subscribeToEvent(subscribeToEventRequest, eventId)
    }

    suspend fun unsubscribeFromEvent(eventId: Int, userId: String): Response<Unit> {
        return eventApi.unsubscribeFromEvent(eventId, userId)
    }

    suspend fun getUserEvents(userId: String, fromDate: String, toDate: String): Response<List<GetEventsResponse>> {
        Log.d("EventRepository", "$userId\n$fromDate\n$toDate")
        return eventApi.getUserEvents(userId, fromDate, toDate)
    }

    suspend fun getConversationEvents(conversationId: Int, fromDate: String, toDate: String): Response<List<GetEventsResponse>> {
        return eventApi.getConversationEvents(conversationId, fromDate, toDate)
    }

    suspend fun getEventParticipants(eventId: Int): Response<List<GetParticipantsResponse>> {
        return eventApi.getEventParticipants(eventId)
    }
}