package com.estexis.kyc.data.repository

import com.estexis.core.common.ApiResult
import com.estexis.kyc.data.remote.KycRemoteSource
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import javax.inject.Inject

interface KycRepository {
    suspend fun getSubmissions(): ApiResult<List<ApiKycSubmissionResponse>>
    suspend fun submitKyc(
        id: String,
        documentType: String,
        documentNumber: String,
        dateOfBirth: String,
        expiryDate: String,
        countryOfIssue: String,
        files: List<String>,
    ): ApiResult<ApiKycSubmitResponse>
}

class KycRepositoryImpl @Inject constructor(
    private val kycRemoteSource: KycRemoteSource,
) : KycRepository {
    override suspend fun getSubmissions(): ApiResult<List<ApiKycSubmissionResponse>> {
        return kycRemoteSource.getSubmissions()
    }

    override suspend fun submitKyc(
        id: String,
        documentType: String,
        documentNumber: String,
        dateOfBirth: String,
        expiryDate: String,
        countryOfIssue: String,
        files: List<String>,
    ): ApiResult<ApiKycSubmitResponse> {
        return kycRemoteSource.submitKyc(
            id = id,
            documentType = documentType,
            documentNumber = documentNumber,
            dateOfBirth = dateOfBirth,
            expiryDate = expiryDate,
            countryOfIssue = countryOfIssue,
            files = files,
        )
    }
}
