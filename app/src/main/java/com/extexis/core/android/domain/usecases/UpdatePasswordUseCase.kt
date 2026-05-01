package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.UpdatePasswordRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.AuthenticationRepository
import javax.inject.Inject

interface UpdatePasswordUseCase {

    suspend fun updatePassword(
        email: String, code: String, password: String, confirmPassword: String
    ): ApiResult<Boolean>

}

class UpdatePasswordUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : UpdatePasswordUseCase {

    override suspend fun updatePassword(
        email: String,
        code: String,
        password: String,
        confirmPassword: String
    ): ApiResult<Boolean> {
        return when (val result = authenticationRepository.updatePassword(
            request = UpdatePasswordRequest(
                email = email,
                code = code,
                newPassword = password,
                confirmPassword = confirmPassword
            )
        )) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }

}
