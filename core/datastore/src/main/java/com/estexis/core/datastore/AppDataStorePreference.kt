package com.estexis.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

abstract class AppDataStorePreference<T>(
    private val dataStore: DataStore<Preferences>,
) : AppPreference<T> {

    protected abstract val defaultValue: T
    protected abstract val key: Preferences.Key<T>

    override suspend fun get(): T = dataStore.data.map { it[key] }.firstOrNull() ?: defaultValue

    override suspend fun set(data: T) {
        dataStore.edit { it[key] = data }
    }

    override suspend fun delete() {
        dataStore.edit { it.remove(key) }
    }

    override fun observe(): Flow<T> = dataStore.data.map { it[key] ?: defaultValue }
}
