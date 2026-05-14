package com.extexis.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class RefreshTokenPreference @Inject constructor(
    dataStore: DataStore<Preferences>,
) : AppDataStorePreference<String>(dataStore) {
    override val defaultValue: String = ""
    override val key: Preferences.Key<String> = PreferencesKey.REFRESH_TOKEN
}
