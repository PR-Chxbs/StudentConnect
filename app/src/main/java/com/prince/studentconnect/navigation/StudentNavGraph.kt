package com.prince.studentconnect.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import androidx.navigation.compose.composable

import com.prince.studentconnect.R
import com.prince.studentconnect.di.ServiceLocator
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.endpoints.student.ui.StudentCalendarScreen
import androidx.compose.material3.Text
import com.prince.studentconnect.ui.endpoints.student.ui.chat.StudentChatScreen
import com.prince.studentconnect.ui.endpoints.student.ui.StudentHomeScreen
import com.prince.studentconnect.ui.endpoints.student.ui.StudentProfileScreen
import com.prince.studentconnect.ui.endpoints.student.ui.StudentSearchScreen
import com.prince.studentconnect.ui.endpoints.student.ui.chat.ChatScreen
import com.prince.studentconnect.ui.endpoints.student.viewmodel.ConversationViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.chat.MessageViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.studentNavGraph(
    navController: NavController,
    conversationViewModel: ConversationViewModel,
    currentUserId: String
) {
    navigation(
        startDestination = Screen.StudentHome.route,
        route = Graph.STUDENT
    ){
        conversationViewModel.instantiate(currentUserId)

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
                        currentRoute = Screen.StudentHome.route
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
                        currentRoute = Screen.StudentSearch.route
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
                        currentRoute = Screen.StudentMessages.route
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
                        currentRoute = Screen.StudentCalendar.route
                    )
                }
            )
        }

        composable(Screen.StudentProfile.route) {
            StudentProfileScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.StudentSearch.route
                    )
                }
            )
        }

        composable(Screen.StudentConversationMessages.route) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getInt("conversation_id") ?: return@composable

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
                members = conversation?.members ?: return@composable
            )
        }
    }
}