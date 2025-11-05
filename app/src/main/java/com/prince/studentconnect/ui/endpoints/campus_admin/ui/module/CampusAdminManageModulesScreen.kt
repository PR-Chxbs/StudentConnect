package com.prince.studentconnect.ui.endpoints.campus_admin.ui.module

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.data.remote.dto.module.GetModulesResponse
import com.prince.studentconnect.ui.components.module.ModuleCard
import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.module.ModuleCmsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusAdminManageModulesScreen(
    viewModel: ModuleCmsViewModel,
    bottomBar: @Composable () -> Unit,
    onEditModuleClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    var showDeleteDialog by remember { mutableStateOf(false) }
    var moduleToDelete by remember { mutableStateOf<GetModulesResponse?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchModules()
    }

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditModuleClick(-1) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Module")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("All Modules") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is ModuleCmsViewModel.UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ModuleCmsViewModel.UiState.Error -> {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                is ModuleCmsViewModel.UiState.Success -> {
                    val modules = uiState.modules
                    if (modules.isEmpty()) {
                        Text(
                            text = "No modules found.",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(modules) { module ->
                                ModuleCard(
                                    module = module,
                                    onEditClick = { moduleId ->
                                        viewModel.selectModule(module)
                                        onEditModuleClick(moduleId)
                                    },
                                    onDeleteClick = {
                                        moduleToDelete = module
                                        showDeleteDialog = true
                                    },
                                    isAdminMode = true
                                )
                            }
                        }
                    }
                }
            }

            // Confirmation Dialog
            if (showDeleteDialog && moduleToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Module") },
                    text = { Text("Are you sure you want to delete \"${moduleToDelete?.name}\"?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val id = moduleToDelete!!.module_id
                                showDeleteDialog = false
                                viewModel.deleteModule(
                                    moduleId = id,
                                    onSuccess = {
                                        Toast.makeText(context, "Module deleted successfully.", Toast.LENGTH_SHORT).show()
                                    },
                                    onError = { msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        ) { Text("Delete", color = MaterialTheme.colorScheme.error) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}