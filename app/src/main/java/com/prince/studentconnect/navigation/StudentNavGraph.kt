package com.prince.studentconnect.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.compose.composable

import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.endpoints.student.ui.calendar.StudentCalendarScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.student.ui.chat.StudentChatScreen
import com.prince.studentconnect.ui.endpoints.student.ui.StudentHomeScreen
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.student.ui.StudentSearchScreen
import com.prince.studentconnect.ui.endpoints.student.ui.calendar.AddEventScreen
import com.prince.studentconnect.ui.endpoints.student.ui.calendar.EventDetailsScreen
import com.prince.studentconnect.ui.endpoints.student.ui.chat.ChatScreen
import com.prince.studentconnect.ui.endpoints.student.ui.settings.SettingsScreen
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.calendar.CalendarViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.studentNavGraph(
    navController: NavController,
    conversationViewModel: ConversationViewModel,
    calendarViewModel: CalendarViewModel,
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel
) {

    navigation(
        startDestination = Screen.StudentHome.route,
        route = Graph.STUDENT
    ){
        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.StudentHome.route,
                label = "Home",
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.StudentSearch.route,
                label = "Search",
                iconRes = R.drawable.ic_search_icon
            ),
            BottomNavItem(
                route = Screen.StudentMessages.route,
                label = "Messages",
                iconRes = R.drawable.ic_chat_icon
            ),
            BottomNavItem(
                route = Screen.StudentCalendar.route,
                label = "Calendar",
                iconRes = R.drawable.ic_calendar_icon
            ),
            BottomNavItem(
                route = Screen.StudentProfile.route,
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.StudentHome.route) {
            StudentHomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.StudentHome.route,
                        authViewModel = authViewModel
                    )
                }
            )
        }

        composable(Screen.StudentSearch.route) {
            StudentSearchScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.StudentSearch.route,
                        authViewModel = authViewModel
                    )
                }
            )
        }

        composable(Screen.StudentMessages.route) {
            StudentChatScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.StudentMessages.route,
                        authViewModel = authViewModel
                    )
                },
                conversationViewModel = conversationViewModel
            )
        }

        composable(Screen.StudentCalendar.route) {
            StudentCalendarScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.StudentCalendar.route,
                        authViewModel = authViewModel
                    )
                },
                viewModel = calendarViewModel
            )
        }

        composable(Screen.StudentProfile.route) { backStackEntry ->
            val currentUserId by authViewModel.currentUserId.collectAsState()

            val userId = backStackEntry.arguments?.getString("user_id") ?: currentUserId

            ProfileScreen(
                userId = userId,
                currentUserId = currentUserId,
                onBackClick = {navController.popBackStack()},
                onSettingsClick = {navController.navigate(Screen.StudentSettings.route)},
                onEditProfileClick = {},
                bottomBar = {
                    if (userId == currentUserId) {
                        BottomNavBar(
                            items = bottomNavItems,
                            navController = navController,
                            currentRoute = Screen.StudentProfile.route,
                            authViewModel = authViewModel
                        )
                    }
                }
            )
        }

        // ------- Chat Extra -------
        composable(Screen.StudentConversationMessages.route) { backStackEntry ->
            val currentUserId by authViewModel.currentUserId.collectAsState()

            val conversationId = backStackEntry.arguments?.getString("conversation_id")?.toIntOrNull() ?: return@composable

            Log.d("StudentNavGraph", "Retrieved Conversation Id: $conversationId")

            val conversation = conversationViewModel.conversations.value
                .firstOrNull { it.id == conversationId }

            if (conversation == null) {
                // Show a simple error or navigate back
                Text("Conversation not found")
                return@composable
            }

            ChatScreen(
                navController = navController,
                conversationId = conversationId,
                userId = currentUserId,
                members = conversation.members,
                conversationName = conversation.name,
                conversationType = conversation.type
            )
        }

        // ------- Calendar Extra -------
        composable(Screen.StudentEventDetails.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("event_id")?.toIntOrNull() ?: return@composable

            // This only runs when eventId changes, not on every recomposition
            LaunchedEffect(eventId) {
                Log.d("EventDetailsScreen", "(StudentNavGraph) Fetching event details for $eventId")
                calendarViewModel.getEventDetails(eventId)
            }

            val event = calendarViewModel.selectedEvent
            Log.d("EventDetailsScreen", "(StudentNavGraph) Event : $event")

            if (event == null) {
                Text("Event not found")
                return@composable
            }

            EventDetailsScreen(
                event = event,
                onBackClick = { navController.popBackStack() }
            )
        }


        composable(Screen.StudentAddEvent.route) {
            val currentUserId by authViewModel.currentUserId.collectAsState()

            AddEventScreen(
                navController = navController,
                viewModel = calendarViewModel,
                currentUserId = currentUserId
            )
        }

        composable(Screen.StudentSettings.route) {
            SettingsScreen(settingsViewModel)
        }
    }
}