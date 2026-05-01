package com.extexis.core.android.domain.usecases

import com.extexis.core.android.data.request.RegistrationRequest
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.domain.repositories.AuthenticationRepository
import javax.inject.Inject

interface RegistrationUseCase {

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ApiResult<Boolean>

}

class RegistrationUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : RegistrationUseCase {

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): ApiResult<Boolean> {
        val result = authenticationRepository.register(
            request = RegistrationRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(result.data.emailSent)
            is ApiResult.Error -> result
        }
    }

}
