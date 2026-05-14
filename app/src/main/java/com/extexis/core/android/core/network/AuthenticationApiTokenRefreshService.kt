package com.extexis.core.android.core.network

import com.extexis.core.network.TokenRefreshApi
import com.extexis.core.network.TokenRefreshRequest
import com.extexis.core.network.TokenRefreshResult
import com.extexis.core.network.TokenRefreshService
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
