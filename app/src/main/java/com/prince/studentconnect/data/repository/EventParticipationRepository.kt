package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.EventParticipationApi
import com.prince.studentconnect.data.remote.dto.event.*
import retrofit2.Response

class EventParticipationRepository(private val eventParticipationApi: EventParticipationApi) {
    suspend fun getEventParticipant(eventId: Int): Response<List<GetParticipantsResponse>> {
        return eventParticipationApi.GetParticipantsResponse(eventId)
    }
}
