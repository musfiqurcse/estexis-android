package com.estexis.otp.domain

import com.estexis.core.common.ApiResult
import com.estexis.otp.data.repository.AccountVerificationRepository
import javax.inject.Inject

interface UpdatePasswordUseCase {
    suspend fun updatePassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
    ): ApiResult<Boolean>
}

class UpdatePasswordUseCaseImpl @Inject constructor(
    private val repository: AccountVerificationRepository,
) : UpdatePasswordUseCase {

    override suspend fun updatePassword(
        email: String,
        code: String,
        newPassword: String,
        confirmPassword: String,
    ): ApiResult<Boolean> {
        return repository.updatePassword(email, code, newPassword, confirmPassword)
    }
}
