package com.extexis.login.data.remote

import com.extexis.core.network.ApiResult
import com.extexis.core.network.executeSafeApiCall
import com.extexis.login.data.api.LoginApi
import com.extexis.login.data.request.LoginRequest
import com.extexis.login.data.response.ApiLoginResponse
import javax.inject.Inject

class LoginRemoteSource @Inject constructor(
    private val loginApi: LoginApi,
) {
    suspend fun login(email: String, password: String): ApiResult<ApiLoginResponse> {
        return executeSafeApiCall {
            loginApi.login(LoginRequest(identifier = email, password = password))
        }
    }
}
