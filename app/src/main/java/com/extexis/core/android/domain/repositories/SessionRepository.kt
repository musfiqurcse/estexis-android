package com.extexis.core.android.domain.repositories

import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

sealed class AuthState {

    data object Authenticated : AuthState()

    data object NotAuthenticated : AuthState()
}

interface SessionRepository {
    val authState: StateFlow<AuthState>

    suspend fun signOut()
}

class SessionRepositoryImpl @Inject constructor(
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference
) : SessionRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.NotAuthenticated)
    override val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        _authState.value = runBlocking {
            getAuthState()
        }
    }

    private suspend fun getAuthState(): AuthState {
        val accessToken = accessTokenPreference.get()
        return if (accessToken.isNotEmpty()) AuthState.Authenticated else AuthState.NotAuthenticated
    }

    override suspend fun signOut() {
        accessTokenPreference.delete()
        refreshTokenPreference.delete()
        _authState.value = AuthState.NotAuthenticated
    }

}
