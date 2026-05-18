package com.estexis.core.android.data.remote

import com.estexis.core.android.data.api.UserApi
import com.estexis.core.android.data.request.ChangePasswordRequest
import com.estexis.core.android.data.request.DeleteAccountRequest
import com.estexis.core.android.data.response.ApiChangePasswordResponse
import com.estexis.core.android.data.response.ApiDeleteAccountResponse
import com.estexis.core.android.data.response.ApiLogOutResponse
import com.estexis.core.network.ApiResult
import com.estexis.core.network.executeSafeApiCall
import javax.inject.Inject

interface UserRemoteSource {

    suspend fun logOut(
        refreshToken: String,
        fromAllDevices: Boolean = false
    ): ApiResult<ApiLogOutResponse>

    suspend fun changePassword(request: ChangePasswordRequest): ApiResult<ApiChangePasswordResponse>

    suspend fun deleteAccount(request: DeleteAccountRequest): ApiResult<ApiDeleteAccountResponse>
}

class UserRemoteSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteSource {

    override suspend fun logOut(
        refreshToken: String,
        fromAllDevices: Boolean
    ): ApiResult<ApiLogOutResponse> {
        return executeSafeApiCall {
            userApi.loginOut(
                refreshToken = refreshToken,
                fromAllDevices = fromAllDevices
            )
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequest): ApiResult<ApiChangePasswordResponse> {
        return executeSafeApiCall { userApi.changePassword(request = request) }
    }

    override suspend fun deleteAccount(request: DeleteAccountRequest): ApiResult<ApiDeleteAccountResponse> {
        return executeSafeApiCall { userApi.deleteAccount(request = request) }
    }
}
