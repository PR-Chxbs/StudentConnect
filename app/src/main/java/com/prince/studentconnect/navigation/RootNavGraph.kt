package com.prince.studentconnect.navigation

import androidx.compose.runtime.Composable
import retrofit2.Retrofit
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    
    // --- Navigation ---
    NavHost(
        navController = navController,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navController = navController)
        studentNavGraph(navController = navController)
        lecturerNavGraph(navController = navController)
        //systemAdminNavGraph(navController = navController)
        //campusAdminNavGraph(navController = navController)
    }
}