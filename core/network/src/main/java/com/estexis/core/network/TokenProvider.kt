package com.estexis.core.network

interface TokenProvider {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun setAccessToken(token: String)
    suspend fun setRefreshToken(token: String)
}
