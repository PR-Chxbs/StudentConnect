package com.prince.studentconnect.ui.endpoints.system_admin.ui.campus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.studentconnect.ui.endpoints.system_admin.viewmodel.campus.CampusCmsViewModel
import com.prince.studentconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCampusScreen(
    viewModel: CampusCmsViewModel,
    isEditMode: Boolean,
    onBack: () -> Unit
) {
    val operationState = viewModel.operationState
    val currentCampus = if (isEditMode) viewModel.currentCampus else null

    var name by remember { mutableStateOf(currentCampus?.name ?: "") }
    var location by remember { mutableStateOf(currentCampus?.location ?: "") }
    var imageUrl by remember { mutableStateOf(currentCampus?.campus_image_url ?: "") }

    val buttonText = if (isEditMode) "Update Campus" else "Create Campus"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Campus" else "Add Campus") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Campus Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(stringResource(R.string.location)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text(stringResource(R.string.image_URL)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isEditMode) {
                        viewModel.updateCampus(
                            campusId = currentCampus?.campus_id ?: return@Button,
                            name = name,
                            location = location,
                            imageUrl = imageUrl
                        )
                    } else {
                        viewModel.addCampus(name, location, imageUrl)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }

            when (operationState) {
                is CampusCmsViewModel.OperationState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is CampusCmsViewModel.OperationState.Success -> {
                    Text(
                        text = stringResource(R.string.operation_successful),
                        color = Color.Green,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                is CampusCmsViewModel.OperationState.Error -> {
                    Text(
                        text = operationState.message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                else -> {}
            }
        }
    }
}
