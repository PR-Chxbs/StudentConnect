package com.prince.studentconnect.ui.endpoints.campus_admin.ui.module

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.data.remote.dto.module.GetModuleResponse
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.EditModuleViewModel

// ---------- Screen ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleCreateEditScreen(
    moduleViewModel: EditModuleViewModel,
    isEditMode: Boolean,
    module: GetModuleResponse? = null,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by moduleViewModel.uiState.collectAsState()

    // Populate fields if editing
    LaunchedEffect(module) {
        moduleViewModel.populateFields(module)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Module" else "Add Module") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = moduleViewModel::onNameChange,
                        label = { Text("Module Name") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.code,
                        onValueChange = moduleViewModel::onCodeChange,
                        label = { Text("Module Code") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )
                }
                item {
                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = moduleViewModel::onDescriptionChange,
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                    )
                }
                item {
                    Button(
                        onClick = {
                            moduleViewModel.submitModule(
                                isEditMode = isEditMode,
                                moduleId = module?.module_id,
                                onSuccess = {
                                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                                },
                                onError = { message ->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(if (uiState.isLoading) "Submitting..." else if (isEditMode) "Update Module" else "Create Module")
                    }
                }
            }
        }
    }

}