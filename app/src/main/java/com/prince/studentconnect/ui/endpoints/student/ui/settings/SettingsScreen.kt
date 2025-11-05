package com.prince.studentconnect.ui.endpoints.student.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val themeMode by viewModel.themeMode.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Settings") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Appearance", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            ThemeOption(
                title = "System Default",
                selected = themeMode == 0
            ) { viewModel.setTheme(0) }

            ThemeOption(
                title = "Light Mode",
                selected = themeMode == 1
            ) { viewModel.setTheme(1) }

            ThemeOption(
                title = "Dark Mode",
                selected = themeMode == 2
            ) { viewModel.setTheme(2) }
        }
    }
}

@Composable
fun ThemeOption(
    title: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        RadioButton(selected = selected, onClick = onSelect)
    }
}
