package com.estexis.changepassword.data.repository

import com.estexis.changepassword.data.remote.ChangePasswordRemoteSource
import com.estexis.core.common.ApiResult
import javax.inject.Inject

interface ChangePasswordRepository {
    suspend fun changePassword(currentPassword: String, newPassword: String): ApiResult<Unit>
}

class ChangePasswordRepositoryImpl @Inject constructor(
    private val remoteSource: ChangePasswordRemoteSource,
) : ChangePasswordRepository {

    override suspend fun changePassword(currentPassword: String, newPassword: String): ApiResult<Unit> =
        remoteSource.changePassword(currentPassword, newPassword)
}
