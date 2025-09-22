package com.prince.studentconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import retrofit2.Retrofit
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.di.ServiceLocator
import androidx.lifecycle.viewmodel.compose.viewModel

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

    // --- Navigation ---
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navController = navController)
        studentNavGraph(
            navController = navController,
            conversationViewModel = conversationViewModel,
            currentUserId = currentUserId
        )
        lecturerNavGraph(navController = navController)
        systemAdminNavGraph(navController = navController)
        campusAdminNavGraph(navController = navController)
    }
}