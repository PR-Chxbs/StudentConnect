package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.CourseApi
import com.prince.studentconnect.data.remote.dto.course.Course
import com.prince.studentconnect.data.remote.dto.course.Campus
import com.prince.studentconnect.data.remote.dto.course.CreateCourseRequest
import com.prince.studentconnect.data.remote.dto.course.CreateCourseResponse
import com.prince.studentconnect.data.remote.dto.course.DeleteCourseResponse
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.data.remote.dto.course.UpdateCourseRequest
import com.prince.studentconnect.data.remote.dto.course.UpdateCourseResponse
import com.prince.studentconnect.data.remote.dto.relationship.LinkCourseModuleRequest
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeCourseApi : CourseApi {

    // In-memory storage for courses
    private val courses = mutableListOf(
        Course(
            course_id = 1,
            name = "Computer Science",
            description = "Learn software development",
            duration_years = 3,
            campus = Campus(campus_id = 1, name = "Main Campus")
        ),
        Course(
            course_id = 2,
            name = "Business Management",
            description = "Learn business principles",
            duration_years = 3,
            campus = Campus(campus_id = 2, name = "City Campus")
        ),
        Course(
            course_id = 3,
            name = "Mechanical Engineering",
            description = "Learn machines and design",
            duration_years = 4,
            campus = Campus(campus_id = 1, name = "Main Campus")
        )
    )
    private var nextId = 4

    override suspend fun getCourses(campusId: Int?): Response<List<GetCoursesResponse>> {
        val filteredCourses = campusId?.let { id ->
            courses.filter { it.campus.campus_id == id }
        } ?: courses

        val returnResponse = mutableListOf<GetCoursesResponse>()

        filteredCourses.forEach { course ->
            val newCourse = GetCoursesResponse(
                course_id = course.course_id,
                name = course.name,
                description = course.description,
                duration_years = course.duration_years,
                campus = course.campus
            )

            returnResponse.add(newCourse)
        }

        return Response.success(returnResponse)
    }

    override suspend fun addCourse(request: CreateCourseRequest): Response<CreateCourseResponse> {
        // Map campus_id to a Campus object (dummy name for now)
        val campus = when (request.campus_id) {
            1 -> Campus(campus_id = 1, name = "Main Campus")
            2 -> Campus(campus_id = 2, name = "City Campus")
            3 -> Campus(campus_id = 3, name = "Coastal Campus")
            else -> Campus(campus_id = request.campus_id, name = "Unknown Campus")
        }

        val newCourse = Course(
            course_id = nextId++,
            name = request.name,
            description = request.description,
            duration_years = request.duration_years,
            campus = campus
        )

        courses.add(newCourse)
        return Response.success(CreateCourseResponse(
            course_id = newCourse.course_id,
            name = newCourse.name,
            description = newCourse.description,
            duration_years = newCourse.duration_years,
            campus = campus
        ))
    }

    override suspend fun updateCourse(request: UpdateCourseRequest, courseId: Int): Response<UpdateCourseResponse> {
        val courseIndex = courses.indexOfFirst { it.course_id == courseId }

        return if (courseIndex != -1) {
            // Map campus_id to a Campus object
            val campus = when (request.campus_id) {
                1 -> Campus(campus_id = 1, name = "Main Campus")
                2 -> Campus(campus_id = 2, name = "City Campus")
                3 -> Campus(campus_id = 3, name = "Coastal Campus")
                else -> Campus(campus_id = request.campus_id, name = "Unknown Campus")
            }

            val oldCourse = courses[courseIndex]
            courses[courseIndex] = oldCourse.copy(
                name = request.name,
                description = request.description,
                duration_years = request.duration_years,
                campus = campus
            )

            Response.success(UpdateCourseResponse(
                course_id = courseId,
                name = request.name,
                description = request.description,
                duration_years = request.duration_years,
                campus = campus
            ))
        } else {
            // Simulate 404 error if course not found
            val errorJson = """{"error":"Course not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteCourse(courseId: Int): Response<DeleteCourseResponse> {
        val removed = courses.removeIf { it.course_id == courseId }
        return if (removed) {
            Response.success(DeleteCourseResponse(
                message = "Course successfully deactivated"
            ))
        } else {
            // Simulate 404 error if course not found
            val errorJson = """{"error":"Course not found"}"""
            Response.error(404, errorJson.toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun linkCourseModule(request: LinkCourseModuleRequest): Response<Unit> {
        TODO("Not yet implemented")
    }
}
