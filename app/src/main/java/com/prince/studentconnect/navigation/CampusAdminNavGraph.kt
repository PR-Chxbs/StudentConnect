package com.prince.studentconnect.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.components.shared.SearchBar
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.*
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.user.SystemAdminManageUsersScreen
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.campusAdminNavGraph(
    navController: NavController,
    currentUserId: String,
    userCmsViewModel: UserCmsViewModel
    ) {

    Log.d("UserCmsViewModel", "Entered campus admin nav graph")


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
                route = Screen.CampusAdminViewProfile.route.replace("{user_id}", currentUserId),
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.CampusAdminHome.route) {
            userCmsViewModel.initialize(currentUserId)
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
            SystemAdminManageUsersScreen(
                viewModel = userCmsViewModel,
                onUserClick = { userId -> navController.navigate(Screen.CampusAdminViewProfile.route.replace("{user_id}", userId))},
                onAddUserClick = {},
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageUsers.route
                    )
                },
                topBar = { SearchBar("Search users...") }
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

        composable(Screen.CampusAdminViewProfile.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("user_id") ?: ""

            if (userId.isBlank()) {
//                Log.e("CampusAdminNavGraph", "Invalid user id: $userId")
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "User not found")
                }
            }

            ProfileScreen(
                userId = userId,
                currentUserId = currentUserId,
                onBackClick = { navController.popBackStack()},
                onSettingsClick = {},
                onEditProfileClick = {},
                bottomBar = {
                    if (userId == currentUserId) {
                        BottomNavBar(
                            items = bottomNavItems,
                            navController = navController,
                            currentRoute = Screen.CampusAdminViewProfile.route.replace("{user_id}", userId)
                        )
                    }
                },
                isAdmin = true
            )
        }
    }
}