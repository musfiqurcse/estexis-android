package com.estexis.core.domain

//import com.estexis.core.common.ApiResult
//import com.estexis.otp.data.repository.OtpRepository
//import javax.inject.Inject
//
//interface ResendOtpUseCase {
//    suspend fun resendForVerification(email: String): ApiResult<Boolean>
//
//    suspend fun resendForForgotPassword(email: String, lastName: String): ApiResult<Boolean>
//}
//
//class ResendOtpUseCaseImpl @Inject constructor(
//    private val repository: OtpRepository,
//) : ResendOtpUseCase {
//
//    override suspend fun resendForVerification(email: String): ApiResult<Boolean> {
//        return repository.resendOtp(email)
//    }
//
//    override suspend fun resendForForgotPassword(email: String, lastName: String): ApiResult<Boolean> {
//        return repository.resendOtpForForgotPassword(email = email, lastName = lastName)
//    }
//}
