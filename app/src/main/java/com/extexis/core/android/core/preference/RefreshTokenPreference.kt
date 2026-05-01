package com.extexis.core.android.core.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class RefreshTokenPreference @Inject constructor(dataStore: DataStore<Preferences>) : AppDataStorePreference<String>(dataStore) {
    override val defaultValue: String
        get() = ""
    override val key: Preferences.Key<String> = PreferencesKey.REFRESH_TOKEN
}
