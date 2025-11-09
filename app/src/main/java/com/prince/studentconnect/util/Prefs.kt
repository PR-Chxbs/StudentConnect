package com.prince.studentconnect.util

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREFS_NAME = "studentconnect_prefs"
    private const val KEY_LANG = "key_language"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLanguage(context: Context, lang: String) {
        prefs(context).edit().putString(KEY_LANG, lang).apply()
    }

    fun getLanguage(context: Context): String? =
        prefs(context).getString(KEY_LANG, null)
}
