package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.endpoints.lecturer.ui.*

fun NavGraphBuilder.lecturerNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.LecturerHome.route,
        route = Graph.LECTURER
    ) {

        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.LecturerHome.route,
                label = "Home",
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.LecturerSearch.route,
                label = "Search",
                iconRes = R.drawable.ic_search_icon
            ),
            BottomNavItem(
                route = Screen.LecturerMessages.route,
                label = "Messages",
                iconRes = R.drawable.ic_chat_icon
            ),
            BottomNavItem(
                route = Screen.LecturerCalendar.route,
                label = "Calendar",
                iconRes = R.drawable.ic_calendar_icon
            ),
            BottomNavItem(
                route = Screen.LecturerProfile.route,
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.LecturerHome.route){
            LecturerHomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.LecturerHome.route
                    )
                }
            )
        }

        composable(Screen.LecturerSearch.route){
            LecturerSearchScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.LecturerSearch.route
                    )
                }
            )
        }

        composable(Screen.LecturerMessages.route){
            LecturerChatScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.LecturerMessages.route
                    )
                }
            )
        }

        composable(Screen.LecturerCalendar.route){
            LecturerCalendarScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.LecturerCalendar.route
                    )
                }
            )
        }

        composable(Screen.LecturerProfile.route){
            LecturerProfileScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.LecturerProfile.route
                    )
                }
            )
        }
    }
}