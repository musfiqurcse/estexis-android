package com.estexis.core.domain

import com.estexis.core.common.ApiResult

interface AuthVerificationRepository {

    suspend fun sendOtpForVerification(email: String, lastName: String): ApiResult<Boolean>
}
