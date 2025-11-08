package com.prince.studentconnect.data.local.Entities

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.User
import kotlinx.datetime.DateTimePeriod

@Entity(tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["creator_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Conversation::class,
            parentColumns = ["conversation_id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["creator_id"]),
        Index(value = ["conversation_id"])
    ])
data class Event(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "event_id")
    val eventId: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "start_at")
    val startAt: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String,
    @ColumnInfo(name = "color_code")
    val colorCode: String,
    @ColumnInfo(name = "recurrence_rule")
    val recurrenceRule: Recurrence_rule,
    @ColumnInfo(name = "reminder_at")
    val reminderAt: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "creator_id")
    val creatorId: String,
    @ColumnInfo(name = "conversation_id")
    val conversationId: Int
)

enum class Recurrence_rule {
    daily, weekly, monthly
}
