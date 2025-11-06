package com.prince.studentconnect.data.local

import androidx.room.*
import kotlinx.datetime.DateTimePeriod

@Entity(tableName = "conversation_members",
    foreignKeys = [
        ForeignKey (
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey (
        entity = Conversation::class,
        parentColumns = ["conversation_id"],
        childColumns = ["conversation_id"],
        onDelete = ForeignKey.CASCADE
    )
    ])
data class Conversation_Member(
    @PrimaryKey(autoGenerate = true)
    val conversation_member_id: Int = 0,
    val status: Status,
    val joined_at: DateTimePeriod,
    val left_at: DateTimePeriod,
    @ColumnInfo(name = "user_id")
    val user_id: String,
    @ColumnInfo(name = "conversation_id")
    val conversation_id: Int
)

enum class Status {
    active, left, removed, banned
}
