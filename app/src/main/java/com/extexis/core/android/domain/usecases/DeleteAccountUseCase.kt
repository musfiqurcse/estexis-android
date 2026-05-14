package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.DeleteAccountRequest
import com.extexis.core.android.domain.repositories.UserRepository
import com.extexis.core.datastore.AccessTokenPreference
import com.extexis.core.datastore.RefreshTokenPreference
import com.extexis.core.network.ApiResult
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
