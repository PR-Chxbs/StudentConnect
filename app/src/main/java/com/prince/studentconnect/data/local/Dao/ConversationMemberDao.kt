package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Conversation_Member

@Dao
interface ConversationMemberDao {
    @Query("SELECT * FROM conversation_members")
    suspend fun getAllConversationMembers(): List<Conversation_Member>

    @Query("SELECT * FROM conversation_members WHERE conversation_member_id = :id")
    suspend fun getConversationMemberById(id: String): Conversation_Member

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationMembers(conversationMember: Conversation_Member)

    @Update
    suspend fun updateConversationMember(conversationMember: Conversation_Member)

    @Delete
    suspend fun deleteConversaationMember(conversationMember: Conversation_Member)
}