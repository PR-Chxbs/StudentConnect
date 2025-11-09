package com.prince.studentconnect.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.prince.studentconnect.data.preferences.UserPreferencesRepository.Companion.DEFAULT_LANG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        // Theme: 0 = System Default, 1 = Light, 2 = Dark
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val ROLE_KEY = stringPreferencesKey("role")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private const val DEFAULT_LANG = "en"
    }

    // --- Theme ---
    val themeMode: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[THEME_MODE_KEY] ?: 0
    }

    suspend fun setThemeMode(mode: Int) {
        context.dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = mode
        }
    }

    // --- User ID ---
    val userIdFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }

    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun clearUserId() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }


    val roleFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[ROLE_KEY]
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { prefs ->
            prefs[ROLE_KEY] = role
        }
    }

    suspend fun clearRole() {
        context.dataStore.edit { prefs ->
            prefs.remove(ROLE_KEY)
        }
    }

    // --- Language ---
    val languageFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: DEFAULT_LANG
    }

    suspend fun saveLanguage(lang: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = lang
        }
    }

    suspend fun clearLanguage() {
        context.dataStore.edit { prefs ->
            prefs.remove(LANGUAGE_KEY)
        }
    }
}
