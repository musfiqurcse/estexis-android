package com.estexis.registration.domain

import com.estexis.core.network.ApiResult
import com.estexis.registration.data.mapper.toRequest
import com.estexis.registration.data.repository.RegistrationRepository
import javax.inject.Inject

interface RegistrationUseCase {
    suspend fun register(params: RegistrationParams): ApiResult<String>
}

class RegistrationUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository,
) : RegistrationUseCase {

    override suspend fun register(params: RegistrationParams): ApiResult<String> {
        return when (val result = repository.register(params.toRequest())) {
            is ApiResult.Error -> result
            is ApiResult.Success -> ApiResult.Success(result.data.message)
        }
    }
}
