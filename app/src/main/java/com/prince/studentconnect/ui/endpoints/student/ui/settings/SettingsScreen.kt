package com.prince.studentconnect.ui.endpoints.student.ui.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.recreate
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.R
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.ui.lang.LanguageSelector
import com.prince.studentconnect.util.LocaleManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val themeMode by viewModel.themeMode.collectAsState()
    val scope = rememberCoroutineScope()
    var currentLang by remember { mutableStateOf(LocaleManager.getLanguageBlocking(context)) }

    // Runs when language changes to apply new locale dynamically
    LaunchedEffect(currentLang) {
        val newContext = LocaleManager.setLocale(context, currentLang)
        // Update the context configuration so stringResources change
        (context as? Activity)?.apply {
            resources.updateConfiguration(
                newContext.resources.configuration,
                newContext.resources.displayMetrics
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(R.string.settings)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(stringResource(R.string.appearance), style = MaterialTheme.typography.titleMedium)
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

            Spacer(Modifier.height(24.dp))

            // Language selector — pass in the proper handler
            LanguageSelector(
                onLanguageChanged = { newLang ->
                    scope.launch {
                        // Save new language
                        viewModel.saveLanguage(newLang)
                        // Update state to trigger LaunchedEffect
                        currentLang = newLang
                    }
                }
            )

            Button(onClick = {
                viewModel.logout()
                onLogout()
            }) {
                Text("Logout")
            }
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
