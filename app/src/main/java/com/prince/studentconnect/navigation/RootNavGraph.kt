package com.prince.studentconnect.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.di.ServiceLocator
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.CreateCourseViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.ViewAllCoursesViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.EditModuleViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.EditModuleViewModelFactory
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.ModuleCmsViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.CreateUserViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    userPrefs: UserPreferencesRepository,
    authViewModel: AuthViewModel
) {

    val currentUserId by authViewModel.currentUserId.collectAsState()

    // --- ViewModels via ViewModelProvider ---
    val conversationViewModel: ConversationViewModel = viewModel(
        factory = ServiceLocator.provideConversationViewModelFactory()
    )

    val calendarViewModel: CalendarViewModel = viewModel (
        factory = ServiceLocator.provideCalendarViewModelFactory()
    )

    val userCmsViewModel: UserCmsViewModel = viewModel (
        factory = ServiceLocator.provideUserCmsViewModelFactory()
    )

    val campusCmsViewModel: CampusCmsViewModel = viewModel (
        factory = ServiceLocator.provideCampusCmsViewModelFactory()
    )

    val onboardingViewModel: OnboardingViewModel = viewModel(
        factory = ServiceLocator.provideOnboardingViewModelFactory()
    )

    val createUserViewModel: CreateUserViewModel = viewModel (
        factory = ServiceLocator.provideCreateUserViewModelFactory()
    )

    val editModuleViewModel: EditModuleViewModel = viewModel (
        factory = ServiceLocator.provideEditModuleViewModelFactory()
    )

    val moduleCmsViewModel: ModuleCmsViewModel = viewModel (
        factory = ServiceLocator.provideModuleCmsViewModelFactory()
    )

    val viewAllCoursesViewModel: ViewAllCoursesViewModel = viewModel(
        factory = ServiceLocator.provideViewAllCoursesViewModelFactory()
    )

    val createCourseViewModel: CreateCourseViewModel = viewModel(
        factory = ServiceLocator.provideCreateUserViewModelFactory()
    )

    LaunchedEffect(currentUserId) {
        if (currentUserId.isNotEmpty()) {
            println("User is logged in with ID: $currentUserId")
            Log.d("RootNavGraph", "(Auth) User is logged in with ID: $currentUserId")

            conversationViewModel.instantiate(currentUserId)
            conversationViewModel.loadConversations()

            calendarViewModel.instantiate(currentUserId)
        }
    }

    // ------ Navigation ------
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(
            navController = navController,
            authViewModel = authViewModel,
            onboardingViewModel = onboardingViewModel
        )

        studentNavGraph(
            navController = navController,
            // View Models
            conversationViewModel = conversationViewModel,
            calendarViewModel = calendarViewModel,
            settingsViewModel = settingsViewModel,
            authViewModel = authViewModel
        )

        lecturerNavGraph(
            navController = navController,
            authViewModel = authViewModel
        )

        systemAdminNavGraph(
            navController = navController,

            // View Models
            userCmsViewModel = userCmsViewModel,
            campusCmsViewModel = campusCmsViewModel,
            authViewModel = authViewModel,
            createUserViewModel = createUserViewModel
        )

        campusAdminNavGraph(
            navController = navController,

            userCmsViewModel = userCmsViewModel,
            authViewModel = authViewModel,
            editModuleViewModel = editModuleViewModel,
            moduleCmsViewModel = moduleCmsViewModel,
            viewAllCoursesViewModel = viewAllCoursesViewModel,
            createCourseViewModel = createCourseViewModel
        )
    }
}