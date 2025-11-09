package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Interest

@Dao
interface InterestDao {
    @Query("SELECT * FROM interests")
    suspend fun getAllInterests(): List<Interest>

    @Query("SELECT * FROM interests WHERE interest_id = :id")
    suspend fun getInterestById(id: Int): Interest

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterest(interest: Interest)

    @Update
    suspend fun udpateInterest(interest: Interest)

    @Delete
    suspend fun deleteInteres(interest: Interest)
}