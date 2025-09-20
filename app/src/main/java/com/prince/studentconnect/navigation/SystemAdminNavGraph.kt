package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.endpoints.system_admin.ui.*

fun NavGraphBuilder.systemAdminNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.SystemAdminHome.route,
        route = Graph.SYSTEM_ADMIN
    ) {
        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.SystemAdminHome.route,
                label = "Home",
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.SystemAdminManageUsers.route,
                label = "Users",
                iconRes = R.drawable.ic_users_icon
            ),
            BottomNavItem(
                route = Screen.SystemAdminManageCampuses.route,
                label = "Campuses",
                iconRes = R.drawable.ic_book_icon
            ),
            BottomNavItem(
                route = Screen.SystemAdminManageInterests.route,
                label = "Interests",
                iconRes = R.drawable.ic_calendar_icon
            ),
            BottomNavItem(
                route = Screen.SystemAdminProfile.route,
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.SystemAdminHome.route) {
            SystemAdminHomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminHome.route
                    )
                }
            )
        }

        composable(Screen.SystemAdminManageUsers.route) {
            SystemAdminManageUsersScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageUsers.route
                    )
                }
            )
        }

        composable(Screen.SystemAdminManageCampuses.route) {
            SystemAdminManageCampusesScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageCampuses.route
                    )
                }
            )
        }

        composable(Screen.SystemAdminManageInterests.route) {
            SystemAdminManageInterestsScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageInterests.route
                    )
                }
            )
        }

        composable(Screen.SystemAdminProfile.route) {
            SystemAdminProfileScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminProfile.route
                    )
                }
            )
        }
    }
}