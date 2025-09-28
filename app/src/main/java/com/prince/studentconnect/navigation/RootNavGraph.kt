package com.prince.studentconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.di.ServiceLocator
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    val currentUserId = "student_1"

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

    // ------ Navigation ------
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(
            navController = navController
        )

        studentNavGraph(
            navController = navController,
            currentUserId = currentUserId,

            // View Models
            conversationViewModel = conversationViewModel,
            calendarViewModel = calendarViewModel
        )

        lecturerNavGraph(navController = navController)

        systemAdminNavGraph(
            navController = navController,
            currentUserId = currentUserId,

            // View Models
            userCmsViewModel = userCmsViewModel,
            campusCmsViewModel = campusCmsViewModel
        )
        campusAdminNavGraph(navController = navController)
    }
}