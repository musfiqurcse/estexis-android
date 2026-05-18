package com.estexis.login.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import com.estexis.login.data.api.LoginApi
import com.estexis.login.data.request.LoginRequest
import com.estexis.login.data.response.ApiLoginResponse
import javax.inject.Inject

class LoginRemoteSource @Inject constructor(
    private val loginApi: LoginApi,
) {
    suspend fun login(email: String, password: String): ApiResult<ApiLoginResponse> {
        return executeSafeApiCall {
            loginApi.login(LoginRequest(email = email, password = password))
        }
    }
}
