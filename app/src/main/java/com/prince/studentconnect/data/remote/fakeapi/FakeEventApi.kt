package com.prince.studentconnect.data.remote.fakeapi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.prince.studentconnect.data.remote.api.EventApi
import com.prince.studentconnect.data.remote.dto.event.*
import com.prince.studentconnect.data.remote.fakeapi.fakedata.sampleEvents
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class FakeEventApi : EventApi {

    // In-memory storage for events
    private var events = mutableListOf<InternalEvent>()
    private var nextId = 1

    init {
        events = sampleEvents.map { event ->
            event.copy(
                participants = event.participants.toMutableList()
            )
        }.toMutableList()
        Log.d("CalendarScreen", "(FakeEventApi) FakeEventApi initialized")
        Log.d("CalendarScreen", "(FakeEventApi) ------------ Events ------------\n ${prettyReturnEvent(events)}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createEvent(request: CreateEventRequest): Response<CreateEventResponse> {
        val createdAt = Instant.now().toString()
        val newEvent = InternalEvent(
            event_id = nextId++,
            creator_id = request.creator_id,
            conversation_id = request.conversation_id,
            title = request.title,
            description = request.description,
            color_code = request.color_code,
            icon_url = request.icon_url,
            start_at = request.start_at,
            recurrence_rule = request.recurrence_rule,
            reminder_at = request.reminder_at,
            created_at = createdAt,
            participants = mutableListOf(
                Participant(
                    user_id = request.creator_id,
                    full_name = "Creator User",
                    status = "accepted",
                    is_creator = true
                )
            )
        )
        events.add(newEvent)

        val response = CreateEventResponse(
            event_id = newEvent.event_id,
            creator_id = newEvent.creator_id,
            conversation_id = newEvent.conversation_id,
            start_at = newEvent.start_at,
            recurrence_rule = newEvent.recurrence_rule,
            reminder_at = newEvent.reminder_at,
            created_at = newEvent.created_at
        )

        return Response.success(response)
    }

    override suspend fun getEvent(eventId: Int): Response<GetAnEventResponse> {
        val event = events.find { it.event_id == eventId }

        return if (event != null) {
            Response.success(
                GetAnEventResponse(
                    event_id = event.event_id,
                    creator_id = event.creator_id,
                    conversation_id = event.conversation_id,
                    title = event.title,
                    description = event.description,
                    color_code = event.color_code,
                    icon_url = event.icon_url,
                    start_at = event.start_at,
                    recurrence_rule = event.recurrence_rule,
                    reminder_at = event.reminder_at,
                    created_at = event.created_at,
                    participants = event.participants.toTypedArray()
                )
            )
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun updateEvent(request: UpdateEventRequest, eventId: Int): Response<Unit> {
        val index = events.indexOfFirst { it.event_id == eventId }

        return if (index != -1) {
            val oldEvent = events[index]

            // Map participant IDs to Participant objects (dummy names, not creator)
            val participants = request.participants.map { userId ->
                Participant(
                    user_id = userId,
                    full_name = "User $userId",
                    status = "accepted",
                    is_creator = userId == request.creator_id
                )
            }.toMutableList()

            events[index] = oldEvent.copy(
                creator_id = request.creator_id,
                conversation_id = request.conversation_id,
                title = request.title,
                description = request.description,
                start_at = request.start_at,
                recurrence_rule = request.recurrence_rule,
                reminder_at = request.reminder_at,
                created_at = request.created_at,
                participants = participants
            )

            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun patchEvent(request: PatchEventRequest, eventId: Int): Response<Unit> {
        val index = events.indexOfFirst { it.event_id == eventId }

        return if (index != -1) {
            val oldEvent = events[index]

            // Update participants if provided
            val updatedParticipants = request.participants?.map { userId ->
                Participant(
                    user_id = userId,
                    full_name = "User $userId",
                    status = "accepted",
                    is_creator = userId == (request.creator_id ?: oldEvent.creator_id)
                )
            }?.toMutableList() ?: oldEvent.participants

            events[index] = oldEvent.copy(
                creator_id = request.creator_id ?: oldEvent.creator_id,
                conversation_id = request.conversation_id ?: oldEvent.conversation_id,
                title = request.title ?: oldEvent.title,
                description = request.description ?: oldEvent.description,
                start_at = request.start_at ?: oldEvent.start_at,
                recurrence_rule = request.recurrence_rule ?: oldEvent.recurrence_rule,
                reminder_at = request.reminder_at ?: oldEvent.reminder_at,
                created_at = request.created_at ?: oldEvent.created_at,
                participants = updatedParticipants
            )

            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteEvent(eventId: Int): Response<Unit> {
        val removed = events.removeIf { it.event_id == eventId }

        return if (removed) {
            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun subscribeToEvent(
        request: SubscribeToEventRequest,
        eventId: Int
    ): Response<SubscribeToEventResponse> {
        val event = events.find { it.event_id == eventId }

        return if (event != null) {
            // Check if participant already exists
            val existing = event.participants.any { it.user_id == request.user_id }
            if (!existing) {
                event.participants.add(
                    Participant(
                        user_id = request.user_id,
                        full_name = "User ${request.user_id}",
                        status = request.status,
                        is_creator = false
                    )
                )
            } else {
                // Update status and custom reminder if participant already exists
                val index = event.participants.indexOfFirst { it.user_id == request.user_id }
                val old = event.participants[index]
                event.participants[index] = old.copy(
                    status = request.status
                )
            }

            val response = SubscribeToEventResponse(
                user_id = request.user_id,
                is_creator = false, // Creator status is handled separately
                status = request.status,
                custom_reminder_at = request.custom_reminder_at
            )

            Response.success(response)
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }


    override suspend fun unsubscribeFromEvent(eventId: Int): Response<Unit> {
        val event = events.find { it.event_id == eventId }

        return if (event != null) {
            // For simplicity, remove all non-creator participants
            event.participants.removeIf { !it.is_creator }
            Response.success(Unit)
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun getUserEvents(
        userId: String,
        fromDate: String?,
        toDate: String?
    ): Response<GetEventsResponse> {
        val userEvents = events.filter { event ->
            // Check if user is a participant
            event.participants.any { it.user_id == userId }
        }.map { internal ->
            Event(
                event_id = internal.event_id,
                title = internal.title,
                start_at = internal.start_at,
                conversation_id = internal.conversation_id,
                color_code = internal.color_code,
                icon_url = internal.icon_url,
                is_subscribed = internal.participants.any { it.user_id == userId },
                recurrence_rule = internal.recurrence_rule
            )
        }.toTypedArray()

        return Response.success(GetEventsResponse(events = userEvents))
    }

    override suspend fun getConversationEvents(
        conversationId: Int,
        fromDate: String?,
        toDate: String?
    ): Response<GetEventsResponse> {
        val conversationEvents = events.filter { it.conversation_id == conversationId }
            .map { internal ->
                Event(
                    event_id = internal.event_id,
                    title = internal.title,
                    start_at = internal.start_at,
                    conversation_id = internal.conversation_id,
                    color_code = internal.color_code,
                    icon_url = internal.icon_url,
                    is_subscribed = internal.participants.isNotEmpty(), // anyone subscribed
                    recurrence_rule = internal.recurrence_rule
                )
            }.toTypedArray()

        return Response.success(GetEventsResponse(events = conversationEvents))
    }

    override suspend fun getEventParticipants(eventId: Int): Response<GetParticipantsResponse> {
        val event = events.find { it.event_id == eventId }

        return if (event != null) {
            Response.success(GetParticipantsResponse(participant = event.participants.toTypedArray()))
        } else {
            val errorJson = """{"error":"Event not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }
}

data class InternalEvent(
    val event_id: Int,
    val creator_id: String,
    val conversation_id: Int?,
    val title: String,
    val description: String,
    val icon_url: String,
    val color_code: String,
    val start_at: String,
    val recurrence_rule: String,
    val reminder_at: String,
    val created_at: String,
    val participants: MutableList<Participant> = mutableListOf()
)

fun prettyReturnEvent(events: List<InternalEvent>): String {
    var returnString = ""

    events.forEach { event ->
        returnString += "Event: ${event.title}\nStart at: ${event.start_at}\nMembers: ${event.participants}\n---------\n"
    }

    return returnString
}