package com.estexis.core.android.domain.usecases

import com.estexis.core.android.data.request.DeleteAccountRequest
import com.estexis.core.android.domain.repositories.UserRepository
import com.estexis.core.datastore.AccessTokenPreference
import com.estexis.core.datastore.RefreshTokenPreference
import com.estexis.core.network.ApiResult
import javax.inject.Inject

interface DeleteAccountUseCase {

    suspend fun deleteAccount(password: String): ApiResult<Boolean>
}

class DeleteAccountUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val accessTokenPreference: AccessTokenPreference,
    private val refreshTokenPreference: RefreshTokenPreference
) : DeleteAccountUseCase {

    override suspend fun deleteAccount(password: String): ApiResult<Boolean> {
        return when (val result = userRepository.deleteAccount(
            request = DeleteAccountRequest(password = password)
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
