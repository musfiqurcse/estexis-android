package com.estexis.core.datastore

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalePreferences @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val prefs = context.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE)

    fun saveLanguage(language: String) {
        prefs.edit { putString(KEY_LANGUAGE, language) }
    }

    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    companion object {
        private const val KEY_LANGUAGE = "app_language"
        private const val DEFAULT_LANGUAGE = "en"
    }
}
