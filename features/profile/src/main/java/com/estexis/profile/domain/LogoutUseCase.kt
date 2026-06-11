package com.estexis.profile.domain

import com.estexis.core.common.ApiResult
import com.estexis.core.datastore.AccessTokenPreference
import com.estexis.core.datastore.RefreshTokenPreference
import com.estexis.profile.data.repository.ProfileRepository
import com.estexis.profile.data.request.LogoutRequest
import javax.inject.Inject

interface LogoutUseCase {
    suspend fun invoke(): ApiResult<Unit>
}

class LogoutUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val refreshTokenPreference: RefreshTokenPreference,
    private val accessTokenPreference: AccessTokenPreference,
) : LogoutUseCase {

    override suspend fun invoke(): ApiResult<Unit> {
        val refreshToken = refreshTokenPreference.get()
        return when (val result = profileRepository.logout(LogoutRequest(refreshToken))) {
            is ApiResult.Success -> {
                accessTokenPreference.delete()
                refreshTokenPreference.delete()
                ApiResult.Success(Unit)
            }
            is ApiResult.Error -> result
        }
    }
}
