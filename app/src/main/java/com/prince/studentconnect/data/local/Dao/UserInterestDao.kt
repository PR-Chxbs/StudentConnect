package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.User_interest

@Dao
interface UserInterestDao {
    @Query("SELECT * FROM user_interests")
    suspend fun getAllUserInterests(): List<User_interest>

    @Query("SELECT * FROM user_interests WHERE user_id = :id")
    suspend fun getUserInterestById(id: String): User_interest

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInterest(userInterest: User_interest)

    @Update
    suspend fun updateUserInterest(userInterest: User_interest)

    @Delete
    suspend fun deleteUserInterest(userInterest: User_interest)
}