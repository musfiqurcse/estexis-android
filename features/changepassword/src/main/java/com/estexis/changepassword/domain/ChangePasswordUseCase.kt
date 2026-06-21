package com.estexis.changepassword.domain

import com.estexis.changepassword.data.repository.ChangePasswordRepository
import com.estexis.core.common.ApiResult
import javax.inject.Inject

interface ChangePasswordUseCase {
    suspend fun invoke(currentPassword: String, newPassword: String): ApiResult<Unit>
}

class ChangePasswordUseCaseImpl @Inject constructor(
    private val repository: ChangePasswordRepository,
) : ChangePasswordUseCase {

    override suspend fun invoke(currentPassword: String, newPassword: String): ApiResult<Unit> =
        repository.changePassword(currentPassword, newPassword)
}
