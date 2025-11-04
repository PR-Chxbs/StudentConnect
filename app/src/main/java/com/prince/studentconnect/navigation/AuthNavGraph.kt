package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.prince.studentconnect.ui.endpoints.auth.ui.LoginScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.PostRegisterScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.RegisterScreen
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.user.UserCmsViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    viewModel: AuthViewModel
) {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = viewModel,
                onRedirectScreen = { screenRoute: String -> navController.navigate(screenRoute) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.PostRegister.route) {
            PostRegisterScreen(
                authViewModel = viewModel,
                navController = navController
            )
        }
    }
}