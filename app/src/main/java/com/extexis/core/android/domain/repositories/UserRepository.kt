package com.extexis.core.android.domain.repositories

import com.extexis.core.android.data.remote.UserRemoteSource
import com.extexis.core.android.data.request.ChangePasswordRequest
import com.extexis.core.android.data.request.DeleteAccountRequest
import com.extexis.core.android.data.response.ApiChangePasswordResponse
import com.extexis.core.android.data.response.ApiDeleteAccountResponse
import com.extexis.core.android.data.response.ApiLogOutResponse
import com.extexis.core.network.ApiResult
import javax.inject.Inject

interface UserRepository {

    suspend fun logOut(
        refreshToken: String,
        fromAllDevices: Boolean = false
    ): ApiResult<ApiLogOutResponse>

    suspend fun changePassword(request: ChangePasswordRequest): ApiResult<ApiChangePasswordResponse>

    suspend fun deleteAccount(request: DeleteAccountRequest): ApiResult<ApiDeleteAccountResponse>

}

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) : UserRepository {

    override suspend fun logOut(
        refreshToken: String,
        fromAllDevices: Boolean
    ): ApiResult<ApiLogOutResponse> {
        return userRemoteSource.logOut(refreshToken = refreshToken, fromAllDevices = fromAllDevices)
    }

    override suspend fun changePassword(request: ChangePasswordRequest): ApiResult<ApiChangePasswordResponse> {
        return userRemoteSource.changePassword(request = request)
    }

    override suspend fun deleteAccount(request: DeleteAccountRequest): ApiResult<ApiDeleteAccountResponse> {
        return userRemoteSource.deleteAccount(request = request)
    }

}
