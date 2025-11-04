package com.prince.studentconnect.ui.endpoints.student.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    navController: NavController,
    bottomBar: @Composable () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Home") }
            )
        },
        bottomBar = bottomBar
    ) { innerPadding ->

        // ------- Placeholder content, replace with real content -------
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Welcome to the Student Home Screen!", style = MaterialTheme.typography.headlineSmall)
        }
    }
}