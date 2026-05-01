package com.extexis.core.android.core.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class AccessTokenPreference @Inject constructor(dataStore: DataStore<Preferences>) : AppDataStorePreference<String>(dataStore) {
    override val defaultValue: String
        get() = ""
    override val key: Preferences.Key<String> = PreferencesKey.ACCESS_TOKEN
}
