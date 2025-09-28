package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.*

fun NavGraphBuilder.campusAdminNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.CampusAdminHome.route,
        route = Graph.CAMPUS_ADMIN
    ) {
        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.CampusAdminHome.route,
                label = "Home",
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.CampusAdminManageUsers.route,
                label = "Users",
                iconRes = R.drawable.ic_users_icon
            ),
            BottomNavItem(
                route = Screen.CampusAdminManageCourses.route,
                label = "Courses",
                iconRes = R.drawable.ic_book_icon
            ),
            BottomNavItem(
                route = Screen.CampusAdminManageModules.route,
                label = "Modules",
                iconRes = R.drawable.ic_book_icon
            ),
            BottomNavItem(
                route = Screen.CampusAdminProfile.route,
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.CampusAdminHome.route) {
            CampusAdminHomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminHome.route
                    )
                }
            )
        }

        composable(Screen.CampusAdminManageUsers.route) {
            CampusAdminManageUsersScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageUsers.route
                    )
                }
            )
        }

        composable(Screen.CampusAdminManageCourses.route) {
            CampusAdminManageCoursesScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageCourses.route
                    )
                }
            )
        }

        composable(Screen.CampusAdminManageModules.route) {
            CampusAdminManageModulesScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageModules.route
                    )
                }
            )
        }

        composable(Screen.CampusAdminProfile.route) {
            CampusAdminProfileScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminProfile.route
                    )
                }
            )
        }
    }
}