package com.prince.studentconnect.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.prince.studentconnect.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

object LocaleManager {
    private const val DEFAULT_LANG = "en"

    fun getLanguage(context: Context): String {
        return Prefs.getLanguage(context) ?: DEFAULT_LANG
    }

    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
            return context
        }
    }

    fun getLanguageBlocking(context: Context): String = runBlocking {
        UserPreferencesRepository(context).languageFlow.first()
    }
}
