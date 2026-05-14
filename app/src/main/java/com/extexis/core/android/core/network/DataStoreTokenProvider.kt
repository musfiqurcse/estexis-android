package com.extexis.core.android.core.network

import com.extexis.core.datastore.AccessTokenPreference
import com.extexis.core.datastore.RefreshTokenPreference
import com.extexis.core.network.TokenProvider
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
