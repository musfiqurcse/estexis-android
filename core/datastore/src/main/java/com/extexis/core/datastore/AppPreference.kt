package com.extexis.core.datastore

import kotlinx.coroutines.flow.Flow

interface AppPreference<T> {
    suspend fun get(): T
    suspend fun set(data: T)
    suspend fun delete()
    fun observe(): Flow<T>
}
