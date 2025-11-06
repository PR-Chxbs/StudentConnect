package com.prince.studentconnect.data.remote.api

import com.prince.studentconnect.data.remote.dto.course.CreateCourseRequest
import com.prince.studentconnect.data.remote.dto.course.CreateCourseResponse
import com.prince.studentconnect.data.remote.dto.course.DeleteCourseResponse
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.data.remote.dto.course.UpdateCourseRequest
import com.prince.studentconnect.data.remote.dto.course.UpdateCourseResponse
import com.prince.studentconnect.data.remote.dto.relationship.LinkCourseModuleRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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