package com.extexis.core.network

interface TokenRefreshService {
    suspend fun refresh(refreshToken: String): TokenRefreshResult?
}

data class TokenRefreshResult(
    val accessToken: String,
    val refreshToken: String,
)
