package com.estexis.core.network.interceptors

import com.estexis.core.network.SessionManager
import com.estexis.core.network.TokenProvider
import com.estexis.core.network.TokenRefreshService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private val refreshMutex = Mutex()

class AuthorizationTokenInterceptor(
    private val tokenProvider: TokenProvider,
    private val tokenRefreshService: TokenRefreshService,
    private val sessionManager: SessionManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = runBlocking { attachToken(chain.request(), isExpired = false) }
        val response = chain.proceed(request)
        if (response.code != 401) return response

        val refreshedRequest = runBlocking { attachToken(chain.request(), isExpired = true) }
        if (refreshedRequest == chain.request()) return response

        response.close()
        return chain.proceed(refreshedRequest)
    }

    private suspend fun attachToken(request: Request, isExpired: Boolean): Request {
        val token = getToken(isExpired) ?: return request
        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }

    private suspend fun getToken(isExpired: Boolean): String? {
        return refreshMutex.withLock {
            val cachedToken = tokenProvider.getAccessToken()
            if (cachedToken.isNotEmpty() && !isExpired) return@withLock cachedToken

            val refreshToken = tokenProvider.getRefreshToken()
            if (refreshToken.isEmpty()) {
                sessionManager.signOut()
                return@withLock null
            }

            val result = tokenRefreshService.refresh(refreshToken)
            if (result == null) {
                sessionManager.signOut()
                return@withLock null
            }

            tokenProvider.setAccessToken(result.accessToken)
            tokenProvider.setRefreshToken(result.refreshToken)
            return@withLock result.accessToken
        }
    }
}
