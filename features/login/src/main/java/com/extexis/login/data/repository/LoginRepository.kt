package com.extexis.login.data.repository

import com.extexis.core.network.ApiResult
import com.extexis.login.data.remote.LoginRemoteSource
import com.extexis.login.data.response.ApiLoginResponse
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(email: String, password: String): ApiResult<ApiLoginResponse>
}

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteSource: LoginRemoteSource,
) : LoginRepository {
    override suspend fun login(email: String, password: String): ApiResult<ApiLoginResponse> {
        return loginRemoteSource.login(email, password)
    }
}
