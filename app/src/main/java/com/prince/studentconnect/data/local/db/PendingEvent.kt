package com.prince.studentconnect.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A pending event saved locally when network fails.
 * Fields mirror the CreateEventRequest from remote DTOs (strings for dates, etc.)
 */
@Entity(tableName = "pending_events")
data class PendingEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val creatorId: String,
    val conversationId: Int?, // nullable
    val title: String,
    val description: String,
    val startAt: String,
    val iconUrl: String,
    val colorCode: String,
    val recurrenceRule: String,
    val reminderAt: String,
    val createdAtMillis: Long = System.currentTimeMillis()
)
