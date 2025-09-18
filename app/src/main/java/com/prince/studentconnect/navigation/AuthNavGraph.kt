package com.prince.studentconnect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.prince.studentconnect.ui.endpoints.auth.ui.LoginScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.PostRegisterScreen
import com.prince.studentconnect.ui.endpoints.auth.ui.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.PostRegister.route) {
            PostRegisterScreen(navController = navController)
        }
    }
}