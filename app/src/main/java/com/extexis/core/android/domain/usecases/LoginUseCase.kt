package com.extexis.core.android.domain.usecases

import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import com.extexis.core.android.data.request.LoginRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.AuthenticationRepository
import javax.inject.Inject

interface LoginUseCase {

    suspend fun login(email: String, password: String): ApiResult<Boolean>

}

class LoginUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference
) : LoginUseCase {

    override suspend fun login(email: String, password: String): ApiResult<Boolean> {

        return when(val result = authenticationRepository.login(request = LoginRequest(identifier = email, password = password))) {
            is ApiResult.Success -> {
                accessTokenPreference.set(result.data.accessToken)
                refreshTokenPreference.set(result.data.refreshToken)
                ApiResult.Success(true)
            }
            is ApiResult.Error -> result
        }
    }

}
