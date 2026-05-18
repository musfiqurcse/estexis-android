package com.estexis.login.data.repository

import com.estexis.core.network.ApiResult
import com.estexis.login.data.remote.LoginRemoteSource
import com.estexis.login.data.response.ApiLoginResponse
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
