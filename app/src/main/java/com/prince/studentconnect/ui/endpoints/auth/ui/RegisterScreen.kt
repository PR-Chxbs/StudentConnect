package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.material3.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.studentconnect.navigation.Screen

@Composable
fun RegisterScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ){
        // ----------- Replace all the below content with the actual UI -----------

        Text("Register Screen")

        Button(
            onClick = {navController.navigate(Screen.PostRegister.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To Post Register")
        }

        Button(
            onClick = {navController.navigate(Screen.Login.route)},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Go To Login")
        }
    }
}