package com.prince.studentconnect.ui.endpoints.student.ui

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
fun StudentSearchScreen(
    navController: NavController,
    bottomBar: @Composable () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Search") }
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
            Text("Welcome to the Student Search Screen!", style = MaterialTheme.typography.headlineSmall)
        }
    }
}