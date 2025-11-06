package com.prince.studentconnect.data.local

import androidx.room.*
import kotlinx.datetime.DateTimePeriod

@Entity(tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["creator_id"],
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
    val event_id: Int = 0,
    val title: String,
    val description: String,
    val start_at: DateTimePeriod,
    val icon_url: String,
    val color_code: String,
    val recurrence_rule: Recurrence_rule,
    val reminder_at: DateTimePeriod,
    val created_at: DateTimePeriod,
    @ColumnInfo(name = "creator_id")
    val creator_id: Int,
    @ColumnInfo(name)
)

enum class Recurrence_rule {
    daily, weekly, monthly
}
