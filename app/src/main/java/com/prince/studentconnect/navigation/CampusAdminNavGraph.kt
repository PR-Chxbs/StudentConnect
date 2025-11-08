package com.prince.studentconnect.navigation

import android.os.Build
import android.text.Selection
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.*
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.course.CreateCourseScreen
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.course.SelectModulesScreen
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.course.ViewAllCoursesScreen
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.module.CampusAdminManageModulesScreen
import com.prince.studentconnect.ui.endpoints.campus_admin.ui.module.ModuleCreateEditScreen
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.CreateCourseViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.ViewAllCoursesViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.EditModuleViewModel
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.ModuleCmsViewModel
import com.prince.studentconnect.ui.endpoints.student.ui.profile.ProfileScreen
import com.prince.studentconnect.ui.endpoints.system_admin.ui.user.SystemAdminManageUsersScreen
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.campusAdminNavGraph(
    navController: NavController,

    // View Models
    userCmsViewModel: UserCmsViewModel,
    authViewModel: AuthViewModel,
    editModuleViewModel: EditModuleViewModel,
    moduleCmsViewModel: ModuleCmsViewModel,
    viewAllCoursesViewModel: ViewAllCoursesViewModel,
    createCourseViewModel: CreateCourseViewModel
    ) {

    navigation(
        startDestination = Screen.CampusAdminManageUsers.route,
        route = Graph.CAMPUS_ADMIN
    ) {
        val bottomNavItems = listOf(
            /*BottomNavItem(
                route = Screen.CampusAdminHome.route,
                label = "Home",
                iconRes = R.drawable.ic_home_icon
            ),*/
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
                route = Screen.CampusAdminViewProfile.route,
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
                        currentRoute = Screen.CampusAdminHome.route,
                        authViewModel = authViewModel
                    )
                }
            )
        }

        composable(Screen.CampusAdminManageUsers.route) {
            val currentUserId by authViewModel.currentUserId.collectAsState()
            userCmsViewModel.initialize(currentUserId)

            SystemAdminManageUsersScreen(
                viewModel = userCmsViewModel,
                onUserClick = { userId -> navController.navigate(Screen.CampusAdminViewProfile.route.replace("{user_id}", userId))},
                onAddUserClick = {},
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageUsers.route,
                        authViewModel = authViewModel
                    )
                },
                topBar = { SearchBar("Search users...") }
            )
        }

        composable(Screen.CampusAdminManageCourses.route) {
            ViewAllCoursesScreen(
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageCourses.route,
                        authViewModel = authViewModel
                    )
                },
                viewModel = viewAllCoursesViewModel,
                onAddCourseClick = { navController.navigate(Screen.CreateCourse.route)},
                onEditCourseClick = {}
            )
        }

        composable(Screen.CampusAdminManageModules.route) {
            CampusAdminManageModulesScreen(
                onEditModuleClick = { moduleId -> navController.navigate(Screen.EditModule.route.replace("{module_id}", "$moduleId"))},
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.CampusAdminManageModules.route,
                        authViewModel = authViewModel
                    )
                },
                viewModel = moduleCmsViewModel
            )
        }

        composable(Screen.CampusAdminViewProfile.route) { backStackEntry ->
            val currentUserId by authViewModel.currentUserId.collectAsState()

            val userId = backStackEntry.arguments?.getString("user_id") ?: ""

            if (userId.isBlank()) {
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
                            currentRoute = Screen.CampusAdminViewProfile.route,
                            authViewModel = authViewModel
                        )
                    }
                },
                isAdmin = true
            )
        }

        composable(Screen.EditModule.route) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("campus_id")?.toIntOrNull()

            val isEditMode = moduleId != null && moduleId != -1

            ModuleCreateEditScreen(
                moduleViewModel = editModuleViewModel,
                isEditMode = isEditMode,
                onBack = { navController.navigate(Screen.CampusAdminManageModules.route) }
            )
        }

        composable(Screen.CreateCourse.route) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("campus_id")?.toIntOrNull()

            val isEditMode = courseId != null && courseId != -1

            CreateCourseScreen(
                viewModel = createCourseViewModel,
                onNextClick = { navController.navigate(Screen.SelectModules.route)},
                bottomBar = {}
            )
        }

        composable(Screen.SelectModules.route) {
            SelectModulesScreen(
                viewModel = createCourseViewModel,
                onCourseCreated = { navController.popBackStack(Screen.CampusAdminManageCourses.route, inclusive = false) },
                bottomBar = {}
            )
        }
    }
}