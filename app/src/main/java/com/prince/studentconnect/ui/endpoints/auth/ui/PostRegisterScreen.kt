package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.studentconnect.navigation.Screen

@Composable
fun PostRegisterScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ){
        // ----------- Replace all the below content with the actual UI -----------

        Text("Post Register Screen", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = {navController.navigate(Screen.SystemAdmin.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To System Admin")
        }

        Button(
            onClick = {navController.navigate(Screen.CampusAdmin.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To Campus Admin")
        }

        Button(
            onClick = {navController.navigate(Screen.Student.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To Student")
        }

        Button(
            onClick = {navController.navigate(Screen.Lecturer.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To Lecturer")
        }
    }
}