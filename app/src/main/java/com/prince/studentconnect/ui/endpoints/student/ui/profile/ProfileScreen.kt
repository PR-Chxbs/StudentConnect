package com.prince.studentconnect.ui.endpoints.student.ui.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.profile.ProfileViewModel
import coil.compose.AsyncImage
import com.prince.studentconnect.di.ServiceLocator

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    currentUserId: String,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    bottomBar: @Composable () -> Unit,
    isAdmin: Boolean = false
) {
    val viewModel: ProfileViewModel = viewModel(
        factory = ServiceLocator.provideProfileViewModelFactory(
            userId = userId
        )
    )

    val isOwnProfile = userId == currentUserId

    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            when (uiState) {
                is ProfileViewModel.UiState.Success -> {
                    TopAppBar(
                        title = {
                            Text("${uiState.user.first_name} ${uiState.user.last_name}")
                        },
                        navigationIcon = {
                            if (!isOwnProfile) {
                                IconButton(onClick = onBackClick) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                }
                            }
                        },
                        actions = {
                            if (isOwnProfile) {
                                IconButton(onClick = onSettingsClick) {
                                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                                }
                            }
                        }
                    )
                }
                else -> {}
            }
        },
        bottomBar = bottomBar
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (uiState) {
                is ProfileViewModel.UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ProfileViewModel.UiState.Error -> {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is ProfileViewModel.UiState.Success -> {
                    val user = uiState.user
                    Log.d("ProfileScreen", "User: $user")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Profile Row
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = user.profile_picture_url,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                user.student_number?.let {
                                    Text("Student #: $it", style = MaterialTheme.typography.bodyMedium)
                                }
                                Text(user.campus.name, style = MaterialTheme.typography.bodyMedium)
                                user.course?.let {
                                    Text(it.name, style = MaterialTheme.typography.bodyMedium)
                                }
                                Text(
                                    text = "Role: ${user.role}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Bio
                        Column {
                            Text("Bio", style = MaterialTheme.typography.titleMedium)
                            Text(user.bio, style = MaterialTheme.typography.bodyMedium)
                        }

                        // Edit button (own profile only)
                        if (isOwnProfile) {
                            Button(
                                onClick = onEditProfileClick,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Edit Profile")
                            }
                        }
                    }
                }
            }
        }
    }
}
