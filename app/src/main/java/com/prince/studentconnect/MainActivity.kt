package com.prince.studentconnect

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import com.prince.studentconnect.data.repository.SupabaseClientProvider
import com.prince.studentconnect.di.ServiceLocator
import com.prince.studentconnect.navigation.RootNavGraph
import com.prince.studentconnect.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.studentconnect.ui.endpoints.student.viewmodel.settings.SettingsViewModel
import com.prince.studentconnect.ui.theme.BaseScreen
import com.prince.studentconnect.ui.theme.StudentConnectTheme
import com.prince.studentconnect.util.LocaleManager
import com.prince.studentconnect.utils.NotificationPermissionRequester

import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var userPrefs: UserPreferencesRepository
    private lateinit var settingsViewModel: SettingsViewModel

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) return
        val lang = LocaleManager.getLanguageBlocking(newBase)
        val context = LocaleManager.setLocale(newBase, lang)
        super.attachBaseContext(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPrefs = UserPreferencesRepository(this)
        settingsViewModel = SettingsViewModel(userPrefs)

        setContent {
            val themeMode by settingsViewModel.themeMode.collectAsState(initial = 0)
            val isDarkTheme = when (themeMode) {
                1 -> false // Light
                2 -> true  // Dark
                else -> isSystemInDarkTheme() // System Default
            }

            var device_token by remember { mutableStateOf("Fetching token...") }

            val authViewModel: AuthViewModel = viewModel(
                factory = ServiceLocator.provideAuthViewModelFactory(userPrefs)
            )

            LaunchedEffect(Unit) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    device_token = if (task.isSuccessful) task.result else "Error getting token"
                    authViewModel.setNewDeviceToken(device_token)
                }
            }

            StudentConnectTheme(isDarkTheme) {
                StudentConnectApp(
                    settingsViewModel = settingsViewModel,
                    authViewModel = authViewModel,
                    userPrefs = userPrefs
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
    authViewModel: AuthViewModel,
    userPrefs: UserPreferencesRepository
) {
    NotificationPermissionRequester()

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