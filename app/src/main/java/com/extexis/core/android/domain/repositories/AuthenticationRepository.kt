package com.extexis.core.android.domain.repositories

import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.data.remote.AuthenticationRemoteSource
import com.extexis.core.android.data.request.LoginRequest
import com.extexis.core.android.data.request.RegistrationRequest
import com.extexis.core.android.data.request.UpdatePasswordRequest
import com.extexis.core.android.data.response.ApiLoginResponse
import com.extexis.core.android.data.response.ApiRegistrationResponse
import com.extexis.core.android.data.response.ApiUpdatePasswordResponse
import javax.inject.Inject

interface AuthenticationRepository {

    suspend fun login(request: LoginRequest): ApiResult<ApiLoginResponse>


    suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse>

    suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse>

}

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationRemoteSource: AuthenticationRemoteSource
) : AuthenticationRepository {

    override suspend fun login(request: LoginRequest): ApiResult<ApiLoginResponse> {
        return authenticationRemoteSource.login(request = request)
    }

    override suspend fun register(request: RegistrationRequest): ApiResult<ApiRegistrationResponse> {
        return authenticationRemoteSource.register(request)
    }

    override suspend fun updatePassword(request: UpdatePasswordRequest): ApiResult<ApiUpdatePasswordResponse> {
        return authenticationRemoteSource.updatePassword(request)
    }

}
