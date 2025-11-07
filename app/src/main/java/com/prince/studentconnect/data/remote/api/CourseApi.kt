package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.course.*
import com.prince.studentconnect.data.remote.dto.relationship.LinkCourseModuleRequest
import retrofit2.Response
import retrofit2.http.*

interface CourseApi {
    @GET("courses")
    suspend fun getCourses(
        @Query("campus_id") campusId: Int? = null
    ): Response<List<GetCoursesResponse>>

    @POST("courses")
    suspend fun addCourse(
        @Body request: CreateCourseRequest
    ): Response<CreateCourseResponse>

    @PUT("courses/{course_id}")
    suspend fun updateCourse(
        @Body request: UpdateCourseRequest,
        @Path("course_id") courseId: Int
    ): Response<UpdateCourseResponse>

    @DELETE("courses/{course_id}")
    suspend fun deleteCourse(
        @Path("course_id") courseId: Int
    ): Response<DeleteCourseResponse>

    @POST("course-modules")
    suspend fun linkCourseModule(
        @Body request: LinkCourseModuleRequest
    ): Response<Unit>
}