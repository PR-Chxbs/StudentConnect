package com.prince.studentconnect.data.local.Dao

import androidx.room.*
import com.prince.studentconnect.data.local.Entities.Course
import com.prince.studentconnect.data.local.Entities.Course_Module

@Dao
interface CourseModuleDao {
    @Query("SELECT * FROM course_modules")
    suspend fun getAllCourseModules(): List<Course_Module>

    @Query("SELECT * FROM course_modules WHERE course_module_id = :id")
    suspend fun getCourseModuleByID(id: Int): Course_Module

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseModule(courseModule: Course_Module)

    @Update
    suspend fun updateCourseModule(courseModule: Course_Module)

    @Delete
    suspend fun deleteCourseModule(courseModule: Course_Module)
}