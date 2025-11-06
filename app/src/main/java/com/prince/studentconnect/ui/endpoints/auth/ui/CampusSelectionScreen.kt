package com.prince.studentconnect.ui.endpoints.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.prince.studentconnect.data.remote.dto.campus.GetCampusesResponse
import com.prince.studentconnect.ui.components.campus.CampusCard
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.onboarding.OnboardingViewModel
import com.prince.studentconnect.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusSelectionScreen(
    onboardingViewModel: OnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val campuses by onboardingViewModel.campuses.collectAsState()
    val selectedCampus by onboardingViewModel.selectedCampus.collectAsState()
    val isLoading by onboardingViewModel.isLoadingCampuses.collectAsState()

    // Fetch campuses once on entry
    LaunchedEffect(Unit) {
        onboardingViewModel.fetchCampuses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Step 2 of 3: Select Campus",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        LinearProgressIndicator(
                            progress = 2f / 3f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                enabled = selectedCampus != null
            ) {
                Text("Next")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                campuses.isEmpty() -> {
                    Text(
                        text = "No campuses available.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(campuses) { campus ->
                            CampusCard(
                                campus = campus,
                                isSelected = selectedCampus?.campus_id == campus.campus_id,
                                onSelect = { onboardingViewModel.selectCampus(campus) },
                                isAdminMode = false,
                                onEditClick = {},
                                onDeleteClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}


/* MIZE THIS */
@Preview(showBackground = true)
@Composable
fun PreviewCampusCard() {
    val sample = GetCampusesResponse(
        campus_id = 1,
        name = "Main Campus",
        location = "Johannesburg, South Africa",
        campus_image_url = "https://via.placeholder.com/150"
    )
    CampusCard(campus = sample, isSelected = true, onSelect = {}, onEditClick = {}, onDeleteClick = {})
}
