package com.prince.studentconnect

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.SupabaseClientProvider
import com.prince.studentconnect.di.ServiceLocator
import com.prince.studentconnect.navigation.RootNavGraph
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.theme.BaseScreen
import com.prince.studentconnect.ui.theme.StudentConnectTheme
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

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

        // Handle intent if app was opened from Google OAuth redirect
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle intent when activity is already running
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val data: Uri? = intent?.data
        if (data != null && data.scheme == "com.prince.studentconnect") {
            // Convert the Uri to a string for Supabase
            val redirectUrl = data.toString()

            // Supabase OAuth redirect received
            lifecycleScope.launch {
                try {
                    SupabaseClientProvider.client.auth.exchangeCodeForSession(redirectUrl)
                    // Update UI or navigate after successful session exchange
                } catch (e: Exception) {
                    // Handle error gracefully (e.g., show a toast or log)
                    Log.e("Auth", "Error exchanging code for session", e)
                }
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
    val authViewModel: AuthViewModel = viewModel(
        factory = ServiceLocator.provideAuthViewModelFactory(userPrefs)
    )

    BaseScreen {
        val navController = rememberNavController()

        RootNavGraph(
            navController = navController,
            settingsViewModel = settingsViewModel,
            userPrefs = userPrefs,
            authViewModel = authViewModel
        )
    }
}
