package com.prince.studentconnect.data.local.Entities

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.User

@Entity(tableName = "event_participations",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["event_id"],
            childColumns = ["event_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["event_id"]),
        Index(value = ["user_id"])
    ])
data class Event_participation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_participation_id")
    val eventParticipationId: Int = 0,
    @ColumnInfo(name = "is_creator")
    val isCreator: Boolean,
    val status: Participation_status,
    @ColumnInfo(name = "event_id")
    val eventId: Int,
    @ColumnInfo(name = "user_id")
    val userId: String
)

enum class Participation_status {
    subscribed, unsubscribed, hidden
}
