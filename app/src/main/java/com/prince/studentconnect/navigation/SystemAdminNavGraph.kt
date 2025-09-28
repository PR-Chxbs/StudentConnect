package com.prince.studentconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
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
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.*
import com.prince.studentconnect.ui.endpoints.system_admin.ui.campus.CampusDetailsScreen
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
                onCampusClick = { campusId: Int -> navController.navigate(Screen.CampusDetails.route.replace("{campus_id}", "$campusId"))},
                onAddCampusClick = {  },
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

        // Extras
        composable(Screen.CampusDetails.route) { backStackEntry ->
            val campusId = backStackEntry.arguments?.getString("campus_id")?.toIntOrNull()

            campusId?.let { campusCmsViewModel.getCampusById(it) }

            when (val uiState = campusCmsViewModel.uiState) {
                is CampusCmsViewModel.UiState.Success -> {
                    CampusDetailsScreen(
                        campus = campusCmsViewModel.currentCampus,
                        onEditClick = { /* navigate to edit campus screen */ },
                        onDeleteClick = { /* show delete dialog */ },
                        onBackClick = { navController.popBackStack() },
                        bottomBar = { /* pass your bottom bar */ }
                    )
                }

                is CampusCmsViewModel.UiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                    )
                }

                else -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}