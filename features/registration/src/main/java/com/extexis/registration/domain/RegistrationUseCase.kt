package com.extexis.registration.domain

import com.extexis.core.network.ApiResult
import com.extexis.registration.data.mapper.toRequest
import com.extexis.registration.data.repository.RegistrationRepository
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
