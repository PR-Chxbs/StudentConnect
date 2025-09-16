package com.prince.studentconnect.data.repository

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

    suspend fun updateEvent(updateEventRequest: UpdateEventRequest, eventId: Int): Response<Any> {
        return eventApi.updateEvent(updateEventRequest, eventId)
    }

    suspend fun patchEvent(patchEventRequest: PatchEventRequest, eventId: Int): Response<Any> {
        return eventApi.patchEvent(patchEventRequest, eventId)
    }

    suspend fun deleteEvent(eventId: Int): Response<Any> {
        return eventApi.deleteEvent(eventId)
    }

    suspend fun subscribeToEvent(subscribeToEventRequest: SubscribeToEventRequest, eventId: Int): Response<SubscribeToEventResponse> {
        return eventApi.subscribeToEvent(subscribeToEventRequest, eventId)
    }

    suspend fun unsubscribeFromEvent(eventId: Int): Response<Any> {
        return eventApi.unsubscribeFromEvent(eventId)
    }

    suspend fun getUserEvents(userId: String, fromDate: String, toDate: String): Response<GetEventsResponse> {
        return eventApi.getUserEvents(userId, fromDate, toDate)
    }

    suspend fun getConversationEvents(conversationId: Int, fromDate: String, toDate: String): Response<GetEventsResponse> {
        return eventApi.getConversationEvents(conversationId, fromDate, toDate)
    }

    suspend fun getEventParticipants(eventId: Int): Response<GetParticipantsResponse> {
        return eventApi.getEventParticipants(eventId)
    }
}