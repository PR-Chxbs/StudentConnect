package com.prince.studentconnect.data.remote.fakeapi

import com.prince.studentconnect.data.remote.api.UserApi
import com.prince.studentconnect.data.remote.dto.user.*
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeUserApi : UserApi {

    private val users = mutableListOf<GetUserResponse>()

    // Example initial data
    init {
        users.add(
            GetUserResponse(
                user_id = "user1",
                first_name = "John",
                last_name = "Doe",
                role = "student",
                bio = "CS Student",
                campus = Campus(1, "Main Campus"),
                course = Course(1, "Computer Science"),
                profile_picture_url = ""
            )
        )
        users.add(
            GetUserResponse(
                user_id = "user2",
                first_name = "Jane",
                last_name = "Smith",
                role = "lecturer",
                bio = "Lecturer in Maths",
                campus = Campus(1, "Main Campus"),
                course = Course(2, "Mathematics"),
                profile_picture_url = ""
            )
        )
    }

    override suspend fun getUser(userId: String): Response<GetUserResponse> {
        val user = users.find { it.user_id == userId }
        return if (user != null) Response.success(user)
        else Response.error(404, """{"error":"User not found"}""".toResponseBody("application/json".toMediaType()))
    }

    override suspend fun getUsers(role: String?, campusId: Int?, courseId: Int?): Response<GetUsersResponse> {
        val filtered = users.filter { user ->
            (role == null || user.role == role) &&
                    (campusId == null || user.campus.campus_id == campusId) &&
                    (courseId == null || user.course?.course_id == courseId)
        }.map { user ->
            User(
                user_id = user.user_id,
                first_name = user.first_name,
                last_name = user.last_name,
                role = user.role,
                course = user.course,
                module = emptyArray() // modules can be added if needed
            )
        }.toTypedArray()
        return Response.success(GetUsersResponse(users = filtered))
    }

    override suspend fun updateUser(request: UpdateUserRequest, userId: String): Response<Unit> {
        val index = users.indexOfFirst { it.user_id == userId }
        return if (index != -1) {
            val old = users[index]
            users[index] = old.copy(
                first_name = request.first_name,
                last_name = request.last_name,
                bio = request.bio,
                campus = Campus(request.campus_id, old.campus.name), // name can be dummy
                course = Course(request.course_id, old.course?.name ?: "Course $userId"),
                profile_picture_url = request.profile_picture_url
            )
            Response.success(Unit)
        } else {
            Response.error(404, """{"error":"User not found"}""".toResponseBody("application/json".toMediaType()))
        }
    }

    override suspend fun deleteUser(userId: String): Response<Unit> {
        val removed = users.removeIf { it.user_id == userId }
        return if (removed) Response.success(Unit)
        else Response.error(404, """{"error":"User not found"}""".toResponseBody("application/json".toMediaType()))
    }

    override suspend fun updateUserRole(request: UpdateUserRoleRequest, userId: String): Response<Unit> {
        val index = users.indexOfFirst { it.user_id == userId }
        return if (index != -1) {
            val old = users[index]
            users[index] = old.copy(role = request.role)
            Response.success(Unit)
        } else {
            Response.error(404, """{"error":"User not found"}""".toResponseBody("application/json".toMediaType()))
        }
    }
}
