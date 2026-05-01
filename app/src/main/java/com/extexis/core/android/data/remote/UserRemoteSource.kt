package com.extexis.core.android.data.remote

import com.extexis.core.android.data.api.UserApi
import com.extexis.core.android.data.request.ChangePasswordRequest
import com.extexis.core.android.data.request.DeleteAccountRequest
import com.extexis.core.android.data.response.ApiChangePasswordResponse
import com.extexis.core.android.data.response.ApiDeleteAccountResponse
import com.extexis.core.android.data.response.ApiLogOutResponse
import com.extexis.core.android.data.util.ApiResult
import com.extexis.core.android.data.util.executeSafeApiCall
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
