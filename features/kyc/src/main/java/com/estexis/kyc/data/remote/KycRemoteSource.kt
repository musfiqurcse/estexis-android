package com.estexis.kyc.data.remote

import com.estexis.core.common.ApiResult
import com.estexis.core.network.executeSafeApiCall
import com.estexis.kyc.data.api.KycApi
import com.estexis.kyc.data.request.KycSubmitRequest
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.data.response.ApiKycSubmitResponse
import javax.inject.Inject

class KycRemoteSource @Inject constructor(
    private val kycApi: KycApi,
) {
    suspend fun getSubmissions(): ApiResult<List<ApiKycSubmissionResponse>> {
        return executeSafeApiCall { kycApi.getSubmissions() }
    }

    suspend fun submitKyc(
        id: String,
        documentType: String,
        documentNumber: String,
        dateOfBirth: String,
        expiryDate: String,
        countryOfIssue: String,
        files: List<String>,
    ): ApiResult<ApiKycSubmitResponse> {
        return executeSafeApiCall {
            kycApi.submitKyc(
                id = id,
                request = KycSubmitRequest(
                    documentType = documentType,
                    documentNumber = documentNumber,
                    dateOfBirth = dateOfBirth,
                    expiryDate = expiryDate,
                    countryOfIssue = countryOfIssue,
                    files = files,
                )
            )
        }
    }
}
