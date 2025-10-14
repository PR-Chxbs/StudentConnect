package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.CourseApi
import com.prince.studentconnect.data.remote.dto.course.*
import retrofit2.Response

class CourseRepository(
    private val courseApi: CourseApi
) {
    suspend fun getCourses(campusId: Int): Response<GetCoursesResponse> {
        return courseApi.getCourses(campusId)
    }

    suspend fun addCourse(createCourseRequest: CreateCourseRequest): Response<Unit> {
        return courseApi.addCourse(createCourseRequest)
    }

    suspend fun updateCourse(updateCourseRequest: UpdateCourseRequest, courseId: Int): Response<Unit> {
        return courseApi.updateCourse(updateCourseRequest, courseId)
    }

    suspend fun deleteCourse(courseId: Int): Response<Unit> {
        return courseApi.deleteCourse(courseId)
    }
}