package com.prince.studentconnect.di

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.fakerepository.FakeAuthRepository
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.remote.api.*
import com.prince.studentconnect.data.remote.dto.conversation.MemberA
import com.prince.studentconnect.data.remote.fakeapi.*
import com.prince.studentconnect.data.remote.websocket.ChatWebSocketClient
import com.prince.studentconnect.data.remote.websocket.FakeChatWebSocketClient
import com.prince.studentconnect.data.remote.websocket.RealChatWebSocketClient
import com.prince.studentconnect.data.repository.*
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModelFactory
import com.prince.studentconnect.ui.endpoints.student.model.chat.MemberUiModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModelFactory
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModelFactory
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModelFactory
import com.prince.studentconnect.ui.endpoints.student.viewmodel.profile.ProfileViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.profile.ProfileViewModelFactory
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModelFactory
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModelFactory
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RequiresApi(Build.VERSION_CODES.O)
object ServiceLocator {

    // ---------------- Toggle flag ----------------
    private const val USE_FAKE_API = false // switch to false for real backend

    private const val SERVER_URL = "https://studentconnect-server-js.onrender.com"

    // ---------------- Retrofit ----------------
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL) // replace with real URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ---------------- API ----------------
    private val campusApi: CampusApi by lazy {
        if (USE_FAKE_API) FakeCampusApi() else retrofit.create(CampusApi::class.java)
    }

    private val conversationApi: ConversationApi by lazy {
        if (USE_FAKE_API) FakeConversationApi() else retrofit.create(ConversationApi::class.java)
    }

    private val courseApi: CourseApi by lazy {
        if (USE_FAKE_API) FakeCourseApi() else retrofit.create(CourseApi::class.java)
    }

    private val eventApi: EventApi by lazy {
        if (USE_FAKE_API) FakeEventApi() else retrofit.create(EventApi::class.java)
    }

    private val moduleApi: ModuleApi by lazy {
        if (USE_FAKE_API) FakeModuleApi() else retrofit.create(ModuleApi::class.java)
    }

    private val userApi: UserApi by lazy {
        if (USE_FAKE_API) FakeUserApi() else retrofit.create(UserApi::class.java)
    }

    // ---------------- WebSocket ----------------
    private val chatWebSocketClient: ChatWebSocketClient by lazy {
        if (USE_FAKE_API) FakeChatWebSocketClient() else RealChatWebSocketClient(SERVER_URL)
    }

    // ---------------- Repository ----------------
    val authRepository by lazy {
        AuthRepository() // if (USE_FAKE_API) FakeAuthRepository() else
    }

    val campusRepository: CampusRepository by lazy {
        CampusRepository(campusApi)
    }

    val conversationRepository: ConversationRepository by lazy {
        ConversationRepository(conversationApi, chatWebSocketClient)
    }

    val courseRepository: CourseRepository by lazy {
        CourseRepository(courseApi)
    }

    val eventRepository: EventRepository by lazy {
        EventRepository(eventApi)
    }

    val moduleRepository: ModuleRepository by lazy {
        ModuleRepository(moduleApi)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(userApi)
    }

    // ---------------- ViewModel Factory ----------------

    // ----- Student Endpoint -----
    // Main
    fun provideConversationViewModelFactory(): ViewModelProvider.Factory {
        return ConversationViewModelFactory(conversationRepository)
    }

    fun provideCalendarViewModelFactory(): ViewModelProvider.Factory {
        return CalendarViewModelFactory(eventRepository)
    }

    fun provideProfileViewModelFactory(userId: String): ViewModelProvider.Factory {
        return ProfileViewModelFactory(userRepository, userId)
    }

    // Extras
    fun provideMessageViewModelFactory(userId: String, conversationId: Int, members: List<MemberUiModel>, conversationName: String): ViewModelProvider.Factory {
        return MessageViewModelFactory(conversationRepository, userId, conversationId, members, conversationName)
    }

    fun provideSettingsViewModelFactory(themePreferenceManager: UserPreferencesRepository): ViewModelProvider.Factory {
        return SettingsViewModelFactory(themePreferenceManager)
    }

    // ----- Admin Endpoint -----
    // Main
    fun provideUserCmsViewModelFactory(): ViewModelProvider.Factory {
        return UserCmsViewModelFactory(userRepository)
    }

    fun provideCampusCmsViewModelFactory(): ViewModelProvider.Factory {
        return CampusCmsViewModelFactory(campusRepository)
    }

    // ----- Auth Endpoint -----
    fun provideAuthViewModelFactory(userPrefs: UserPreferencesRepository): ViewModelProvider.Factory {
        return AuthViewModelFactory(
            authRepository = authRepository,
            userRepository = userRepository,
            userPrefs = userPrefs
        )
    }
}
