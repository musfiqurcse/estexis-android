package com.extexis.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val HAS_SEEN_GREETINGS_SCREEN = booleanPreferencesKey("has_seen_greetings")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}
