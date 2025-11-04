package com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.studentconnect.data.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.data.remote.dto.course.GetCoursesResponse
import com.prince.studentconnect.data.remote.dto.user.CreateUserRequest
import com.prince.studentconnect.data.repository.CampusRepository
import com.prince.studentconnect.data.repository.CourseRepository
import com.prince.studentconnect.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class OnboardingViewModel(
    private val userRepository: UserRepository,
    private val campusRepository: CampusRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {

    // --- Personal details state ---
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _middleName = MutableStateFlow<String?>(null)
    val middleName: StateFlow<String?> = _middleName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _studentNumber = MutableStateFlow("")
    val studentNumber: StateFlow<String> = _studentNumber

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _bio = MutableStateFlow("")
    val bio: StateFlow<String> = _bio

    private val _profilePictureUrl = MutableStateFlow("")
    val profilePictureUrl: StateFlow<String> = _profilePictureUrl

    // --- Auth-provided values ---
    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _role = MutableStateFlow("student")
    val role: StateFlow<String> = _role

    // --- Campus state ---
    private val _campuses = MutableStateFlow<List<GetCampusesResponse>>(emptyList())
    val campuses: StateFlow<List<GetCampusesResponse>> = _campuses

    private val _selectedCampus = MutableStateFlow<GetCampusesResponse?>(null)
    val selectedCampus: StateFlow<GetCampusesResponse?> = _selectedCampus

    private val _isLoadingCampuses = MutableStateFlow(false)
    val isLoadingCampuses: StateFlow<Boolean> = _isLoadingCampuses

    // --- Course state ---
    private val _courses = MutableStateFlow<List<GetCoursesResponse>>(emptyList())
    val courses: StateFlow<List<GetCoursesResponse>> = _courses

    private val _selectedCourse = MutableStateFlow<GetCoursesResponse?>(null)
    val selectedCourse: StateFlow<GetCoursesResponse?> = _selectedCourse

    private val _isLoadingCourses = MutableStateFlow(false)
    val isLoadingCourses: StateFlow<Boolean> = _isLoadingCourses

    // --- POST result ---
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _submissionSuccess = MutableStateFlow<Boolean?>(null)
    val submissionSuccess: StateFlow<Boolean?> = _submissionSuccess

    // --- Mutators for personal details ---
    fun setFirstName(value: String) { _firstName.value = value }
    fun setMiddleName(value: String?) { _middleName.value = value }
    fun setLastName(value: String) { _lastName.value = value }
    fun setStudentNumber(value: String) { _studentNumber.value = value }
    fun setPhoneNumber(value: String) { _phoneNumber.value = value }
    fun setBio(value: String) { _bio.value = value }
    fun setProfilePictureUrl(value: String) { _profilePictureUrl.value = value }

    // --- Auth info from AuthViewModel (copied at first screen) ---
    fun setAuthInfo(userId: String, email: String) {
        _userId.value = userId
        _email.value = email
    }

    // --- Campus functions ---
    fun fetchCampuses() {
        viewModelScope.launch {
            _isLoadingCampuses.value = true
            try {
                val response = campusRepository.getCampuses()
                if (response.isSuccessful) {
                    _campuses.value = response.body() ?: emptyList()
                } else {
                    _campuses.value = emptyList()
                }
            } catch (e: Exception) {
                _campuses.value = emptyList()
            } finally {
                _isLoadingCampuses.value = false
            }
        }
    }

    fun selectCampus(campus: GetCampusesResponse) {
        _selectedCampus.value = campus
    }

    // --- Course functions ---
    fun fetchCourses(campusId: Int) {
        viewModelScope.launch {
            _isLoadingCourses.value = true
            try {
                val response = courseRepository.getCourses(campusId)
                if (response.isSuccessful) {
                    _courses.value = response.body() ?: emptyList()
                } else {
                    _courses.value = emptyList()
                }
            } catch (e: Exception) {
                _courses.value = emptyList()
            } finally {
                _isLoadingCourses.value = false
            }
        }
    }

    fun selectCourse(course: GetCoursesResponse) {
        _selectedCourse.value = course
    }

    // --- Final submission ---
    fun submitUser() {
        val currentCampus = _selectedCampus.value
        val currentCourse = _selectedCourse.value

        if (_userId.value.isBlank() || _email.value.isBlank()) {
            Log.e("OnboardingScreen", "(OnboardingViewModel) user and/or email is blank")
            Log.e("OnboardingScreen", "(OnboardingViewModel) User: ${_userId.value}      Email: ${_email.value}")
            _submissionSuccess.value = false
            return
        }

        val request = CreateUserRequest(
            user_id = _userId.value,
            first_name = _firstName.value,
            middle_name = _middleName.value,
            last_name = _lastName.value,
            student_number = _studentNumber.value,
            email = _email.value,
            phone_number = _phoneNumber.value,
            role = _role.value,
            bio = _bio.value,
            campus_id = currentCampus?.campus_id,
            course_id = currentCourse?.course_id,
            profile_picture_url = _profilePictureUrl.value
        )

        // Log.d("OnboardingScreen", "(OnboardingViewModel) Request: $request")

        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val response = userRepository.createUser(request)
                _submissionSuccess.value = response.isSuccessful

                if (!response.isSuccessful) {
                    Log.e("OnboardingScreen", "Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            } catch (e: Exception) {
                Log.e("OnboardingScreen", "Error: ${e.message}")
                _submissionSuccess.value = false
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    // --- Reset function (if needed) ---
    fun resetSubmissionState() {
        _submissionSuccess.value = null
    }
}