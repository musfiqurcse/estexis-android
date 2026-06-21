package com.estexis.changepassword.data.remote

import com.estexis.changepassword.data.api.ChangePasswordApi
import com.estexis.changepassword.data.api.ChangePasswordRequest
import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import javax.inject.Inject

interface ChangePasswordRemoteSource {
    suspend fun changePassword(currentPassword: String, newPassword: String): ApiResult<Unit>
}

class ChangePasswordRemoteSourceImpl @Inject constructor(
    private val api: ChangePasswordApi,
) : ChangePasswordRemoteSource {

    override suspend fun changePassword(currentPassword: String, newPassword: String): ApiResult<Unit> {
        val result = executeSafeApiCall { api.changePassword(ChangePasswordRequest(currentPassword, newPassword)) }
        return when (result) {
            is ApiResult.Success -> ApiResult.Success(Unit)
            is ApiResult.Error -> result
        }
    }
}
