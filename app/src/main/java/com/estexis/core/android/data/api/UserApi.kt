package com.estexis.core.android.data.api

import com.estexis.core.android.data.request.ChangePasswordRequest
import com.estexis.core.android.data.request.DeleteAccountRequest
import com.estexis.core.android.data.response.ApiChangePasswordResponse
import com.estexis.core.android.data.response.ApiDeleteAccountResponse
import com.estexis.core.android.data.response.ApiLogOutResponse
import com.estexis.core.network.NetworkConfig
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST(NetworkConfig.LOG_OUT)
    suspend fun loginOut(
        @Query("refresh_token") refreshToken: String,
        @Query("logout_all") fromAllDevices: Boolean = false
    ): Response<ApiLogOutResponse>

    @POST(NetworkConfig.CHANGE_PASSWORD)
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ApiChangePasswordResponse>

    @HTTP(method = "DELETE", path = NetworkConfig.DELETE_ACCOUNT, hasBody = true)
    suspend fun deleteAccount(@Body request: DeleteAccountRequest): Response<ApiDeleteAccountResponse>
}
