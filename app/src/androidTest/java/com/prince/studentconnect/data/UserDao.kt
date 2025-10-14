package com.prince.studentconnect.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY fullName")
    suspend fun getAll(): List<AppUser>

    @Query("SELECT * FROM users WHERE campusId = :campusId ORDER BY fullName")
    suspend fun getByCampus(campusId: Long): List<AppUser>

    @Insert suspend fun insert(user: AppUser): Long
    @Update suspend fun update(user: AppUser)
    @Delete suspend fun delete(user: AppUser)
}
