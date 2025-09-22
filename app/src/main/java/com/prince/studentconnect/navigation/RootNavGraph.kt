package com.prince.studentconnect.navigation

import androidx.compose.runtime.Composable
import retrofit2.Retrofit
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.di.ServiceLocator
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    val currentUserId = "student_1"

    // --- ViewModels via ViewModelProvider ---
    val conversationViewModel: ConversationViewModel = viewModel(
        factory = ServiceLocator.provideConversationViewModelFactory(currentUserId)
    )

    // --- Navigation ---
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navController = navController)
        studentNavGraph(
            navController = navController,
            conversationViewModel = conversationViewModel
        )
        lecturerNavGraph(navController = navController)
        systemAdminNavGraph(navController = navController)
        campusAdminNavGraph(navController = navController)
    }
}