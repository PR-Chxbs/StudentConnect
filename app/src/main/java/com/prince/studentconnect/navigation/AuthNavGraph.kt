package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.prince.studentconnect.ui.endpoints.auth.ui.CampusSelectionScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.CourseSelectionScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.LoginScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.PersonalDetailsScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.RegisterScreen
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel,
    onboardingViewModel: OnboardingViewModel
) {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onRedirectScreen = { screenRoute: String -> navController.navigate(screenRoute) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        // --- Onboarding flow ---
        composable(Screen.OnboardingPersonalDetails.route) {
            PersonalDetailsScreen(
                authViewModel = authViewModel,
                onboardingViewModel = onboardingViewModel,
                onNext = { navController.navigate(Screen.OnboardingCampus.route) }
            )
        }

        composable(Screen.OnboardingCampus.route) {
            CampusSelectionScreen(
                onboardingViewModel = onboardingViewModel,
                onNext = { navController.navigate(Screen.OnboardingCourse.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.OnboardingCourse.route) {
            CourseSelectionScreen(
                onboardingViewModel = onboardingViewModel,
                onNext = {
                    navController.navigate(Graph.STUDENT) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}