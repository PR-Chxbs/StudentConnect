package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel
import com.prince.studentconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(
    authViewModel: AuthViewModel,
    onboardingViewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val context = LocalContext.current

    // Bind state from ViewModel
    val firstName by onboardingViewModel.firstName.collectAsState()
    val middleName by onboardingViewModel.middleName.collectAsState()
    val lastName by onboardingViewModel.lastName.collectAsState()
    val studentNumber by onboardingViewModel.studentNumber.collectAsState()
    val phoneNumber by onboardingViewModel.phoneNumber.collectAsState()
    val bio by onboardingViewModel.bio.collectAsState()
    val profilePictureUrl by onboardingViewModel.profilePictureUrl.collectAsState()

    val authId = authViewModel.currentUserId.collectAsState().value
    val authEmail = authViewModel.currentUserEmail.collectAsState().value

    // Copy user info from AuthViewModel (run once)
    LaunchedEffect(Unit) {
        onboardingViewModel.setAuthInfo(authId, authEmail ?: "")
    }

    // Validation logic
    val maxNameLength = 40
    val maxBioLength = 200
    val nameTooLong = firstName.length > maxNameLength || lastName.length > maxNameLength
    val bioTooLong = bio.length > maxBioLength
    val isFormValid = firstName.isNotBlank()
            && lastName.isNotBlank()
            && phoneNumber.isNotBlank()
            && !nameTooLong
            && !bioTooLong

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.step),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        LinearProgressIndicator(
                            progress = 1f / 3f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Typically not shown on first step */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = isFormValid
            ) {
                Text(stringResource(R.string.next))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { onboardingViewModel.setFirstName(it.take(maxNameLength)) },
                label = { Text(stringResource(R.string.name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            if (firstName.length >= maxNameLength) {
                Text(
                    text = "First name too long (max $maxNameLength chars)",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = middleName ?: "",
                onValueChange = { onboardingViewModel.setMiddleName(it.take(maxNameLength)) },
                label = { Text(stringResource(R.string.name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { onboardingViewModel.setLastName(it.take(maxNameLength)) },
                label = { Text(stringResource(R.string.name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            if (lastName.length >= maxNameLength) {
                Text(
                    text = "Last name too long (max $maxNameLength chars)",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = studentNumber,
                onValueChange = { onboardingViewModel.setStudentNumber(it.take(15)) },
                label = { Text(stringResource(R.string.student_number)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { onboardingViewModel.setPhoneNumber(it.take(15)) },
                label = { Text(stringResource(R.string.phone_number)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { onboardingViewModel.setBio(it.take(maxBioLength)) },
                label = { Text(stringResource(R.string.bio)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            if (bio.length >= maxBioLength) {
                Text(
                    text = "Bio too long (max $maxBioLength chars)",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = profilePictureUrl,
                onValueChange = { onboardingViewModel.setProfilePictureUrl(it) },
                label = { Text(stringResource(R.string.profile_picture)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPersonalDetailsScreen() {
    // Placeholder preview
    Text("Preview: PersonalDetailsScreen")
}