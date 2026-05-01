package com.extexis.core.android.data.interceptors

import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import com.extexis.core.android.data.api.AuthenticationApi
import com.extexis.core.android.domain.repositories.SessionRepository
import com.extexis.core.android.data.request.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private val refreshMutex = Mutex()

class AuthorizationTokenInterceptor(
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference,
    private val authenticationApi: AuthenticationApi,
    private val sessionRepository: SessionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = runBlocking {
            attachToken(chain.request(), isExpired = false)
        }
        val response = chain.proceed(request)
        if (response.code != 401) {
            return response
        }
        response.close()
        val refreshedRequest = runBlocking {
            attachToken(chain.request(), isExpired = true)
        }
        if (refreshedRequest == chain.request()) {
            return response
        }
        return chain.proceed(refreshedRequest)
    }

    private suspend fun attachToken(
        request: Request,
        isExpired: Boolean
    ): Request {
        val token = getToken(isExpired) ?: return request

        return request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }

    private suspend fun getToken(isExpired: Boolean): String? {
        return refreshMutex.withLock {
            val cachedToken = accessTokenPreference.get()
            if (cachedToken.isNotEmpty() && !isExpired) {
                return@withLock cachedToken
            }
            val refreshToken = refreshTokenPreference.get()
            if (refreshToken.isEmpty()) {
                sessionRepository.signOut()
                return@withLock null
            }
            val response = authenticationApi.refreshToken(
                RefreshTokenRequest(refreshToken)
            )
            if (!response.isSuccessful) {
                sessionRepository.signOut()
                return@withLock null
            }
            val body = response.body()
            val newToken = body?.accessToken
            if (newToken.isNullOrEmpty()) {
                sessionRepository.signOut()
                return@withLock null
            }
            accessTokenPreference.set(newToken)
            refreshTokenPreference.set(body.refreshToken)
            return@withLock newToken
        }
    }

}
