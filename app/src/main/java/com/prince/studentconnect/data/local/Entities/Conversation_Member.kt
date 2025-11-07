package com.prince.studentconnect.data.local.Entities

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.User
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
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["conversation_id"])
    ])

data class Conversation_Member(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conversation_member_id")
    val conversationMemberId: Int = 0,
    val status: Status,
    @ColumnInfo(name = "joined_at")
    val joinedAt: DateTimePeriod,
    @ColumnInfo(name = "left_at")
    val leftAt: DateTimePeriod,
    @ColumnInfo(name = "user_id", index = true)
    val userId: String,
    @ColumnInfo(name = "conversation_id", index = true)
    val conversationId: Int
)

enum class Status {
    active, left, removed, banned
}
