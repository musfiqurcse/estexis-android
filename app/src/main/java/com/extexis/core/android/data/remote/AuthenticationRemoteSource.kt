package com.extexis.core.android.data.remote

import com.extexis.core.android.data.api.AuthenticationApi
import com.extexis.core.android.data.request.LoginRequest
import com.extexis.core.android.data.request.RegistrationRequest
import com.extexis.core.android.data.request.UpdatePasswordRequest
import com.extexis.core.android.data.response.ApiLoginResponse
import com.extexis.core.android.data.response.ApiRegistrationResponse
import com.extexis.core.android.data.response.ApiUpdatePasswordResponse
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.data.util.executeSafeApiCall
import javax.inject.Inject

interface AuthenticationRemoteSource {

    suspend fun login(request: LoginRequest): ApiResult<ApiLoginResponse>

    suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse>

    suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse>

}

class AuthenticationRemoteSourceImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi
) : AuthenticationRemoteSource {

    override suspend fun login(request: LoginRequest): ApiResult<ApiLoginResponse> {
        return executeSafeApiCall { authenticationApi.login(request = request) }
    }

    override suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse> {
        return executeSafeApiCall { authenticationApi.register(request = request) }
    }

    override suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse> {
        return executeSafeApiCall { authenticationApi.updatePassword(request = request) }
    }
}
