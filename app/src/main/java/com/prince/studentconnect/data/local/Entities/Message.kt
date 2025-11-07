package com.prince.studentconnect.data.local.Entities

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.User
import kotlinx.datetime.DateTimePeriod

@Entity(tableName = "messsages",
    foreignKeys = [
        ForeignKey (
            entity = Conversation::class,
            parentColumns =["conversation_id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
            ),
        ForeignKey (
            entity = User::class,
            parentColumns = ["sender_id"],
            childColumns = ["sender_id"]
        )
    ],
    indices = [
        Index(value = ["sender_id"]),
        Index(value = ["conversation_id"])
    ])

data class Message(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "message_id")
    val messageId: Int = 0,
    @ColumnInfo(name = "message_text")
    val messageText: String,
    @ColumnInfo(name = "attachment_url")
    val attachmentUrl: String? = null,
    @ColumnInfo(name = "attachment_type")
    val attachmentType: Attachment_type? = null,
    @ColumnInfo(name = "sent_at")
    val sentAt: DateTimePeriod,
    @ColumnInfo(name = "conversation_id")
    val conversationId: Int
)

enum class Attachment_type {
    image, pdf, doc
}
