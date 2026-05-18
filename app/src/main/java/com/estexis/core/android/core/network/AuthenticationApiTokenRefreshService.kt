package com.estexis.core.android.core.network

import com.estexis.core.network.TokenRefreshApi
import com.estexis.core.network.TokenRefreshRequest
import com.estexis.core.network.TokenRefreshResult
import com.estexis.core.network.TokenRefreshService
import javax.inject.Inject

class AuthenticationApiTokenRefreshService @Inject constructor(
    private val tokenRefreshApi: TokenRefreshApi,
) : TokenRefreshService {

    override suspend fun refresh(refreshToken: String): TokenRefreshResult? {
        val response = tokenRefreshApi.refreshToken(TokenRefreshRequest(refreshToken = refreshToken))
        val body = response.body() ?: return null
        return TokenRefreshResult(
            accessToken = body.accessToken,
            refreshToken = body.refreshToken,
        )
    }
}
