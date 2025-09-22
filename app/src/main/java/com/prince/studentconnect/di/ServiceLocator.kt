package com.prince.studentconnect.di

import AuthRepository
import androidx.lifecycle.ViewModelProvider
import com.prince.studentconnect.data.fakerepository.FakeAuthRepository
import com.prince.studentconnect.data.remote.api.*
import com.prince.studentconnect.data.remote.fakeapi.*
import com.prince.studentconnect.data.remote.websocket.ChatWebSocketClient
import com.prince.studentconnect.data.remote.websocket.FakeChatWebSocketClient
import com.prince.studentconnect.data.remote.websocket.RealChatWebSocketClient
import com.prince.studentconnect.data.repository.*
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    // ---------------- Toggle flag ----------------
    private const val USE_FAKE_API = true // switch to false for real backend

    private const val SERVER_URL: String = "https://your-api-base-url.com/"

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
    val chatWebSocketClient: ChatWebSocketClient by lazy {
        FakeChatWebSocketClient()
    }

    /*// ---------------- WebSocket ----------------
    val chatWebSocketClient: ChatWebSocketClient by lazy {
        if (USE_FAKE_API) FakeChatWebSocketClient() else RealChatWebSocketClient(SERVER_URL)
    }*/

    // ---------------- Repository ----------------
    val authRepository by lazy {
        if (USE_FAKE_API) FakeAuthRepository() else AuthRepository()
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
    val conversationViewModelFactory: ViewModelProvider.Factory by lazy {
        ConversationViewModelFactory(conversationRepository)
    }
}
