package com.prince.studentconnect.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.*
import com.prince.studentconnect.ui.endpoints.system_admin.ui.campus.CampusDetailsScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.campus.EditCampusScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.campus.SystemAdminManageCampusesScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.user.CreateUserScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.user.SystemAdminManageUsersScreen
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.CreateUserViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.systemAdminNavGraph(
    navController: NavController,

    // View Model
    userCmsViewModel: UserCmsViewModel,
    campusCmsViewModel: CampusCmsViewModel,
    authViewModel: AuthViewModel,
    createUserViewModel: CreateUserViewModel
) {
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
                route = Screen.SystemAdminViewProfile.route,
                label = "Profile",
                iconRes = R.drawable.ic_user_icon
            )
        )

        composable(Screen.SystemAdminHome.route) {
            userCmsViewModel.initialize()
            campusCmsViewModel.initialize()

            SystemAdminHomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminHome.route,
                        authViewModel = authViewModel
                    )
                }
            )
        }

        composable(Screen.SystemAdminManageUsers.route) {
            SystemAdminManageUsersScreen(
                viewModel = userCmsViewModel,
                onUserClick = { userId -> navController.navigate(Screen.SystemAdminViewProfile.route.replace("{user_id}", userId))},
                onAddUserClick = { navController.navigate(Screen.CreateUser.route) },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageUsers.route,
                        authViewModel = authViewModel
                    )
                },
                topBar = { SearchBar("Search users...") }
            )
        }

        composable(Screen.SystemAdminManageCampuses.route) {
            SystemAdminManageCampusesScreen(
                viewModel = campusCmsViewModel,
                onCampusClick = { campusId -> navController.navigate(Screen.CampusDetails.route.replace("{campus_id}", "$campusId"))},
                onAddCampusClick = { navController.navigate(Screen.EditCampus.route) },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.SystemAdminManageCampuses.route,
                        authViewModel = authViewModel
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
                        currentRoute = Screen.SystemAdminManageInterests.route,
                        authViewModel = authViewModel
                    )
                }
            )
        }

        composable(Screen.SystemAdminViewProfile.route) { backStackEntry ->
            val currentUserId by authViewModel.currentUserId.collectAsState()

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
                            currentRoute = Screen.SystemAdminViewProfile.route,
                            authViewModel = authViewModel
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
                        onEditClick = { currentCampusId -> navController.navigate(Screen.EditCampus.route.replace("{campus_id}", "$currentCampusId")) },
                        onDeleteClick = { currentCampusId ->
                            {
                                campusCmsViewModel.deleteCampusById(
                                    currentCampusId
                                )

                                navController.navigate(Screen.SystemAdminManageCampuses.route)
                            } },
                        onBackClick = { navController.popBackStack() },
                        bottomBar = { /* pass your bottom bar */ }
                    )
                }

                is CampusCmsViewModel.UiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }

                else -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }

        composable(Screen.EditCampus.route) { backStackEntry ->
            val campusId = backStackEntry.arguments?.getString("campus_id")?.toIntOrNull()

            val isEditMode = campusId != null && campusId != -1

            EditCampusScreen(
                viewModel = campusCmsViewModel,
                isEditMode = isEditMode,
                onBack = {navController.popBackStack()}
            )
        }

        composable(Screen.CreateUser.route) {
            CreateUserScreen(
                viewModel = createUserViewModel,
                onBack = { navController.popBackStack() },
                onNavBack = { navController.navigate(Screen.SystemAdminManageUsers.route) }
            )
        }
    }
}