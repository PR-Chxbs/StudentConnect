package com.prince.studentconnect.data.local.Entities

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Module
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = ["module_id"],
            childColumns = ["module_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["module_id"])]
)
data class Conversation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conversation_id")
    val conversationId: Int = 0,
    val name: String? = null,
    @ColumnInfo(name = "max_members")
    val maxMembers: Int,
    val type: Type,
    val visibility: Visibility,
    @ColumnInfo(name = "member_count")
    val memberCount: Int,
    @ColumnInfo(name = "date_created")
    val dateCreated: LocalDateTime,
    @ColumnInfo(name = "module_id")
    val moduleId: Int
)

enum class Type {
    private, group, module_default
}

enum class Visibility {
    public, private, restricted
}
