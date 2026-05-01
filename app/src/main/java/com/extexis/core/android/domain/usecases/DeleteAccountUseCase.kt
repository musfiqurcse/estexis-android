package com.extexis.core.android.domain.usecases

import com.extexis.core.android.core.preference.AccessTokenPreference
import com.extexis.core.android.core.preference.RefreshTokenPreference
import com.extexis.core.android.data.request.DeleteAccountRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.UserRepository
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
