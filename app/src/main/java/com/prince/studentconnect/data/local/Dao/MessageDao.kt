package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messsages")
    suspend fun getAllMessages(): List<Message>

    @Query("SELECT * FROM messsages WHERE message_id = :id")
    suspend fun getMessageById(id: Int): Message

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Update
    suspend fun updateMessage(message: Message)

    @Delete
    suspend fun delteMessage(message: Message)
}