package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Conversation

@Dao
interface ConversationDao {
    @Query(value = "SELECT * FROM conversations")
    suspend fun getAllConversations(): List<Conversation>

    @Query("SELECT * FROM conversations WHERE conversation_id = :id")
    suspend fun getConversationById(id: Int): Conversation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation)

    @Update
    suspend fun updateConversations(conversation: Conversation)

    @Delete
    suspend fun deleteConversation(conversation: Conversation)
}