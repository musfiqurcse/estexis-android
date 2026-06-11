package com.estexis.kyc.domain

import com.estexis.core.common.ApiResult
import com.estexis.kyc.data.repository.KycRepository
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import javax.inject.Inject

data class SubmitKycParams(
    val id: String,
    val documentType: String,
    val documentNumber: String,
    val dateOfBirth: String,
    val expiryDate: String,
    val countryOfIssue: String,
    val files: List<String>,
)

interface SubmitKycUseCase {
    suspend fun invoke(params: SubmitKycParams): ApiResult<ApiKycSubmitResponse>
}

class SubmitKycUseCaseImpl @Inject constructor(
    private val kycRepository: KycRepository,
) : SubmitKycUseCase {
    override suspend fun invoke(params: SubmitKycParams): ApiResult<ApiKycSubmitResponse> {
        return kycRepository.submitKyc(
            id = params.id,
            documentType = params.documentType,
            documentNumber = params.documentNumber,
            dateOfBirth = params.dateOfBirth,
            expiryDate = params.expiryDate,
            countryOfIssue = params.countryOfIssue,
            files = params.files,
        )
    }
}
