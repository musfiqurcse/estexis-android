package com.estexis.core.android.core.network

import com.estexis.core.datastore.AccessTokenPreference
import com.estexis.core.datastore.RefreshTokenPreference
import com.estexis.core.network.TokenProvider
import javax.inject.Inject

class DataStoreTokenProvider @Inject constructor(
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference,
) : TokenProvider {

    override suspend fun getAccessToken(): String = accessTokenPreference.get()

    override suspend fun getRefreshToken(): String = refreshTokenPreference.get()

    override suspend fun setAccessToken(token: String) = accessTokenPreference.set(token)

    override suspend fun setRefreshToken(token: String) = refreshTokenPreference.set(token)
}
