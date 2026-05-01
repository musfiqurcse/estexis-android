package com.extexis.core.android.core.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class AppOnBoardingPreference @Inject constructor(dataStore: DataStore<Preferences>) : AppDataStorePreference<Boolean>(dataStore) {
    override val defaultValue: Boolean
        get() = false
    override val key: Preferences.Key<Boolean> = PreferencesKey.HAS_SEEN_GREETINGS_SCREEN
}
