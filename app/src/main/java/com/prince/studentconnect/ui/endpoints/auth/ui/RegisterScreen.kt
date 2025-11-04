package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Register", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { viewModel.register() },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isLoading) "Registering..." else "Register")
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("Already have an account? Login")
            }

            state.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            state.successMessage?.let {
                Spacer(Modifier.height(12.dp))
                Text(text = it, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}