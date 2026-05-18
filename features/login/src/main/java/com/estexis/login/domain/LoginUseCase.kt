package com.estexis.login.domain

import com.estexis.core.common.ApiResult
import com.estexis.core.network.TokenProvider
import com.estexis.login.data.repository.LoginRepository
import javax.inject.Inject

interface LoginUseCase {
    suspend fun login(email: String, password: String): ApiResult<Boolean>
}

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenProvider: TokenProvider,
) : LoginUseCase {

    override suspend fun login(email: String, password: String): ApiResult<Boolean> {
        return when (val result = loginRepository.login(email, password)) {
            is ApiResult.Success -> {
                tokenProvider.setAccessToken(result.data.accessToken)
                tokenProvider.setRefreshToken(result.data.refreshToken)
                ApiResult.Success(true)
            }
            is ApiResult.Error -> result
        }
    }
}
