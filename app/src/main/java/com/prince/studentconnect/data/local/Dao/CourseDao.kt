package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): List<Course>

    @Query("SELECT * FROM courses WHERE course_id = :id")
    suspend fun getCourseById(id: Int): Course

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: Course)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)
}