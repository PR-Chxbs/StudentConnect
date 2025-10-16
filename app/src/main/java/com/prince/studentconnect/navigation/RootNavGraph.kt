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
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    userPrefs: UserPreferencesRepository
) {

    val context = LocalContext.current
    val userPrefs = UserPreferencesRepository(context)
    val currentUserId by userPrefs.userIdFlow.collectAsState(initial = null)

    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            println("User is logged in with ID: $currentUserId")
            Log.d("RootNavGraph", "(Auth) User is logged in with ID: $currentUserId")
        }
    }

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

    val authViewModel: AuthViewModel = viewModel(
        factory = ServiceLocator.provideAuthViewModelFactory(userPrefs)
    )

    // ------ Navigation ------
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(
            navController = navController,
            viewModel = authViewModel
        )

        studentNavGraph(
            navController = navController,
            currentUserId = currentUserId ?: "",

            // View Models
            conversationViewModel = conversationViewModel,
            calendarViewModel = calendarViewModel,
            settingsViewModel = settingsViewModel
        )

        lecturerNavGraph(navController = navController)

        systemAdminNavGraph(
            navController = navController,
            currentUserId = currentUserId ?: "",

            // View Models
            userCmsViewModel = userCmsViewModel,
            campusCmsViewModel = campusCmsViewModel
        )

        campusAdminNavGraph(
            navController = navController,
            currentUserId = currentUserId ?: "",

            userCmsViewModel = userCmsViewModel
        )
    }
}