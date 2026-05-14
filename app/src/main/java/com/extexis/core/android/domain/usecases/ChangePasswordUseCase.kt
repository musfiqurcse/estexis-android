package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.ChangePasswordRequest
import com.extexis.core.network.ApiResult
import com.extexis.core.android.domain.repositories.UserRepository
import javax.inject.Inject

interface ChangePasswordUseCase {

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ApiResult<Boolean>

}

class ChangePasswordUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : ChangePasswordUseCase {

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ApiResult<Boolean> {
        return when (val result = userRepository.changePassword(
            request = ChangePasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
                confirmPassword = confirmPassword
            )
        )) {
            is ApiResult.Success -> {
                ApiResult.Success(true)
            }

            is ApiResult.Error -> result
        }
    }
}
