package com.prince.studentconnect

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.navigation.RootNavGraph
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.theme.BaseScreen
import com.prince.studentconnect.ui.theme.StudentConnectTheme


class MainActivity : ComponentActivity() {
    private lateinit var usePrefs: UserPreferencesRepository
    private lateinit var settingsViewModel: SettingsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usePrefs = UserPreferencesRepository(this)

        settingsViewModel = SettingsViewModel(usePrefs)

        setContent {
            val themeMode by settingsViewModel.themeMode.collectAsState(initial = 0)

            val isDarkTheme = when (themeMode) {
                1 -> false // Light
                2 -> true  // Dark
                else -> isSystemInDarkTheme() // System Default
            }

            StudentConnectTheme(isDarkTheme) {
                StudentConnectApp(
                    settingsViewModel = settingsViewModel,
                    userPrefs = usePrefs
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentConnectApp(
    settingsViewModel: SettingsViewModel,
    userPrefs: UserPreferencesRepository
) {
    BaseScreen {
        val navController = rememberNavController()

        RootNavGraph(
            navController = navController,
            settingsViewModel = settingsViewModel,
            userPrefs = userPrefs
        )
    }
}
