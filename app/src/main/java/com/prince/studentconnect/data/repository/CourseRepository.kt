package com.prince.studentconnect.data.repository

import com.prince.studentconnect.data.remote.api.CourseApi
import com.prince.studentconnect.data.remote.dto.course.*
import com.prince.studentconnect.data.remote.dto.relationship.LinkCourseModuleRequest
import retrofit2.Response

class CourseRepository(
    private val courseApi: CourseApi
) {
    suspend fun getCourses(campusId: Int): Response<List<GetCoursesResponse>> {
        return courseApi.getCourses(campusId)
    }

    suspend fun addCourse(createCourseRequest: CreateCourseRequest): Response<CreateCourseResponse> {
        return courseApi.addCourse(createCourseRequest)
    }

    suspend fun updateCourse(updateCourseRequest: UpdateCourseRequest, courseId: Int): Response<UpdateCourseResponse> {
        return courseApi.updateCourse(updateCourseRequest, courseId)
    }

    suspend fun deleteCourse(courseId: Int): Response<DeleteCourseResponse> {
        return courseApi.deleteCourse(courseId)
    }

    suspend fun linkCourseModule(linkCourseModuleRequest: LinkCourseModuleRequest): Response<Unit> {
        return courseApi.linkCourseModule(linkCourseModuleRequest)
    }
}