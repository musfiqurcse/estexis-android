package com.estexis.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class AppOnBoardingPreference @Inject constructor(
    dataStore: DataStore<Preferences>,
) : AppDataStorePreference<Boolean>(dataStore) {
    override val defaultValue: Boolean = false
    override val key: Preferences.Key<Boolean> = PreferencesKey.HAS_SEEN_GREETINGS_SCREEN
}
