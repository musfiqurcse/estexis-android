package com.extexis.login.data.api

import com.extexis.core.network.NetworkConfig
import com.extexis.login.data.request.LoginRequest
import com.extexis.login.data.response.ApiLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(NetworkConfig.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<ApiLoginResponse>
}
