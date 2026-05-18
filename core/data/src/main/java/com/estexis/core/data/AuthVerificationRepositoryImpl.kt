package com.estexis.core.data

import com.estexis.core.common.ApiResult
import com.estexis.core.domain.AuthVerificationRepository
import com.estexis.core.network.AuthVerificationApi
import com.estexis.core.network.executeSafeApiCall
import com.estexis.core.network.request.SendOtpRequest
import javax.inject.Inject

class AuthVerificationRepositoryImpl @Inject constructor(
    private val api: AuthVerificationApi,
) : AuthVerificationRepository {

    override suspend fun sendOtpForVerification(email: String, lastName: String): ApiResult<Boolean> {
        val result = executeSafeApiCall {
            api.sendOtpForVerification(
                SendOtpRequest(email = email, lastName = lastName),
            )
        }
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(true)
            is ApiResult.Error -> result
        }
    }
}
