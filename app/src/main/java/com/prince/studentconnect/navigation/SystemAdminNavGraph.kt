package com.prince.studentconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.studentconnect.R
import com.prince.studentconnect.ui.components.shared.BottomNavBar
import com.prince.studentconnect.ui.components.shared.BottomNavItem
import com.prince.studentconnect.ui.components.shared.SearchBar
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.*
import com.prince.studentconnect.ui.endpoints.system_admin.ui.campus.SystemAdminManageCampusesScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.user.SystemAdminManageUsersScreen
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.systemAdminNavGraph(
    navController: NavController,
    currentUserId: String,

    // View Model
    userCmsViewModel: UserCmsViewModel,
    campusCmsViewModel: CampusCmsViewModel
) {
    navigation(
        startDestination = Screen.SystemAdminHome.route,
        route = Graph.SYSTEM_ADMIN
    ) {
        userCmsViewModel.initialize()
        campusCmsViewModel.initialize()

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
                route = Screen.SystemAdminViewProfile.route.replace("{user_id}", currentUserId),
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
                viewModel = userCmsViewModel,
                onUserClick = { userId -> navController.navigate(Screen.SystemAdminViewProfile.route.replace("{user_id}", userId))},
                onAddUserClick = {},
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageUsers.route
                    )
                },
                topBar = { SearchBar("Search users...") }
            )
        }

        composable(Screen.SystemAdminManageCampuses.route) {
            SystemAdminManageCampusesScreen(
                viewModel = campusCmsViewModel,
                onCampusClick = { campusId: Int -> navController.navigate(Screen.SystemAdminManageCampuses.route.replace("{campus_id}", "$campusId"))},
                onAddCampusClick = {},
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageCampuses.route
                    )
                },
                topBar = { SearchBar("Search campuses...") },
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

        composable(Screen.SystemAdminViewProfile.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("user_id") ?: currentUserId

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
                            currentRoute = Screen.SystemAdminViewProfile.route.replace("{user_id}", currentUserId)
                        )
                    }
                },
                isAdmin = true
            )
        }
    }
}