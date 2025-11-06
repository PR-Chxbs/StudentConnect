package com.prince.studentconnect.data.local

import androidx.room.*
import kotlinx.datetime.DateTimePeriod

@Entity(tableName = "messsages",
    ForeignKeys = [
        foreignKey (
            entity = Conversation::class,
            parentColumns =["conversation_id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
            ),
        foreignKey (
            entity = User::class,
            parentColumns = ["sender_id"],
            childColumns = ["sender_id"]
        )
    ])
data class Message(
    @PrimaryKey (autoGenerate = true)
    val message_id: Int = 0,
    val message_text: String,
    val attachment_url: String? = null,
    val attachment_type: Attachment_type? = null,
    val sent_at: DateTimePeriod
)

enum class Attachment_type {
    image, pdf, doc
}
