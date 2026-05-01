package com.extexis.core.android.data.api

import com.extexis.core.android.data.Config
import com.extexis.core.android.data.request.ChangePasswordRequest
import com.extexis.core.android.data.request.DeleteAccountRequest
import com.extexis.core.android.data.response.ApiChangePasswordResponse
import com.extexis.core.android.data.response.ApiDeleteAccountResponse
import com.extexis.core.android.data.response.ApiLogOutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST(Config.LOG_OUT)
    suspend fun loginOut(
        @Query("refresh_token") refreshToken: String,
        @Query("logout_all") fromAllDevices: Boolean = false
    ): Response<ApiLogOutResponse>

    @POST(Config.CHANGE_PASSWORD)
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ApiChangePasswordResponse>

    @HTTP(method = "DELETE", path = Config.DELETE_ACCOUNT, hasBody = true)
    suspend fun deleteAccount(@Body request: DeleteAccountRequest): Response<ApiDeleteAccountResponse>

}
