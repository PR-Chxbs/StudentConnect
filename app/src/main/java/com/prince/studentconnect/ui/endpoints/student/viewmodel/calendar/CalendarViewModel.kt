package com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.event.CreateEventRequest
import com.prince.studentconnect.data.remote.dto.event.Event
import com.prince.studentconnect.data.repository.EventRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel(
    private val repository: EventRepository,
) : ViewModel() {

    private lateinit var userId: String // Needed for fetching user events
    private var isInitialized = false

    // --- State ---
    var currentMonth by mutableStateOf(YearMonth.now())
        private set

    var selectedDate by mutableStateOf<LocalDate?>(LocalDate.now())
        private set

    var eventsByDate by mutableStateOf<Map<LocalDate, List<Event>>>(emptyMap())
        private set

    var eventsForSelectedDate by mutableStateOf<List<Event>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // --- Initialization ---
    fun instantiate(userId: String) {
        if (isInitialized) return
        isInitialized = false
        this.userId = userId
        loadEventsForMonth(currentMonth)
        selectedDate?.let { loadEventsForDate(it) }
    }

    // --- Functions ---

    fun selectDate(date: LocalDate) {
        selectedDate = date
        loadEventsForDate(date)
    }

    fun changeMonth(newMonth: YearMonth) {
        currentMonth = newMonth
        loadEventsForMonth(newMonth)
    }

    fun onEventClick(event: Event) {
        // TODO: Handle navigation to Event Details screen
    }

    // --- Private functions to fetch events ---

    private fun loadEventsForMonth(month: YearMonth) {
        if (!isInitialized) return
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                // Compute start and end of month in ISO-8601 format
                val startOfMonth = month.atDay(1).toString()
                val endOfMonth = month.atEndOfMonth().toString()

                val response = repository.getUserEvents(userId, startOfMonth, endOfMonth)
                if (response.isSuccessful) {
                    val events = response.body()?.events ?: emptyArray()
                    // Map events by LocalDate
                    eventsByDate = events.groupBy { LocalDate.parse(it.start_at.substring(0, 10)) }
                    // Update today's events if selectedDate is in this month
                    selectedDate?.let { loadEventsForDate(it) }
                } else {
                    errorMessage = "Failed to load events: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    private fun loadEventsForDate(date: LocalDate) {
        if (!isInitialized) return
        eventsForSelectedDate = eventsByDate[date] ?: emptyList()
    }

    // Optional: add event
    fun addEvent(request: CreateEventRequest, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        if (!isInitialized) return
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.createEvent(request)
                if (response.isSuccessful) {
                    // Reload month events to update calendar
                    loadEventsForMonth(currentMonth)
                    onSuccess()
                } else {
                    onError("Failed to create event: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }

    // Optional: delete event
    fun deleteEvent(eventId: Int, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        if (!isInitialized) return
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.deleteEvent(eventId)
                if (response.isSuccessful) {
                    loadEventsForMonth(currentMonth)
                    onSuccess()
                } else {
                    onError("Failed to delete event: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }
}
