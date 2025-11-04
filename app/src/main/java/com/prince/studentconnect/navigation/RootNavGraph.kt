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



    LaunchedEffect(currentUserId) {
        if (!currentUserId.isNullOrEmpty()) {
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
            viewModel = authViewModel
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
            authViewModel = authViewModel
        )

        campusAdminNavGraph(
            navController = navController,
            userCmsViewModel = userCmsViewModel,
            authViewModel = authViewModel
        )
    }
}