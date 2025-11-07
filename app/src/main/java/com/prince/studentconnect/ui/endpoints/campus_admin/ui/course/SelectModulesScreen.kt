package com.prince.studentconnect.ui.endpoints.campus_admin.ui.course

import com.prince.studentconnect.ui.endpoints.campus_admin.viewmodel.course.CreateCourseViewModel
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.ui.components.module.ModuleCard
import com.prince.studentconnect.R

@Composable
fun SelectModulesScreen(
    viewModel: CreateCourseViewModel,
    onCourseCreated: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val modules by viewModel.modules.collectAsState()
    val selectedModules by viewModel.selectedModuleIds.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadModules()
    }

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (selectedModules.isEmpty()) {
                        Toast.makeText(context, stringResource(R.string.select_module), Toast.LENGTH_SHORT).show()
                        return@ExtendedFloatingActionButton
                    }

                    viewModel.createCourse(
                        onSuccess = {
                            Toast.makeText(context, stringResource(R.string.course_created), Toast.LENGTH_SHORT).show()
                            onCourseCreated()
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(stringResource(R.string.course_created))
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                modules.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.module_unavailable),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(modules) { module ->
                            val isSelected = selectedModules.contains(module.module_id)
                            Box(
                                modifier = Modifier
                                    .clickable { viewModel.toggleModuleSelection(module.module_id) }
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = MaterialTheme.shapes.medium,
                                    border = BorderStroke(
                                        2.dp,
                                        if (isSelected)
                                            MaterialTheme.colorScheme.primary
                                        else Color.Gray.copy(alpha = 0.2f)
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected)
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.TopEnd
                                    ) {
                                        ModuleCard(
                                            module = module,
                                            onEditClick = {},
                                            onDeleteClick = {},
                                            isAdminMode = false
                                        )

                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Selected",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
