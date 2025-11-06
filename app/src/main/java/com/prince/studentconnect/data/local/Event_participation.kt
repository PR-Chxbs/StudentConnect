package com.prince.studentconnect.data.local

import androidx.room.*

@Entity(tableName = "event_participations",
    ForeignKeys = [
        foreignKey(
            entity = Event::class,
            parentColumns = ["event_id"],
            childColumns = ["event_id"],
            onDelete = ForeignKey.CASCADE
        ),
        foreignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Event_participation(
    @PrimaryKey(autoGenerate = true)
    val event_participation_id: Int = 0,
    val is_creator: Boolean,
    val status: Participation_status
)

enum class Participation_status {
    subscribed, unsubscribed, hidden
}
