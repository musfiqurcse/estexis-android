package com.estexis.core.android.domain.usecases

import com.estexis.core.android.domain.repositories.UserRepository
import com.estexis.core.datastore.AccessTokenPreference
import com.estexis.core.datastore.RefreshTokenPreference
import com.estexis.core.network.ApiResult
import javax.inject.Inject

interface LogOutUseCase {

    suspend fun logOut(): ApiResult<Boolean>
}

class LogOutUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference
) : LogOutUseCase {

    override suspend fun logOut(): ApiResult<Boolean> {

        return when (val result = userRepository.logOut(
            refreshToken = refreshTokenPreference.get(), fromAllDevices = true
        )) {
            is ApiResult.Success -> {
                accessTokenPreference.delete()
                refreshTokenPreference.delete()
                ApiResult.Success(true)
            }
            is ApiResult.Error -> result
        }
    }
}
