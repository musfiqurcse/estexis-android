package com.estexis.kyc.domain

import com.estexis.core.common.ApiResult
import com.estexis.kyc.data.repository.KycRepository
import com.estexis.kyc.data.response.ApiKycSubmissionResponse
import com.estexis.kyc.ui.KycStatus
import javax.inject.Inject

interface GetKycSubmissionsUseCase {
    suspend fun invoke(): ApiResult<List<KycSubmission>>
}

class GetKycSubmissionsUseCaseImpl @Inject constructor(
    private val kycRepository: KycRepository,
) : GetKycSubmissionsUseCase {
    override suspend fun invoke(): ApiResult<List<KycSubmission>> {
        return when (val result = kycRepository.getSubmissions()) {
            is ApiResult.Success -> ApiResult.Success(result.data.map { it.toDomain() })
            is ApiResult.Error -> result
        }
    }
}

private fun ApiKycSubmissionResponse.toDomain() = KycSubmission(
    id = id,
    method = when (method) {
        "nid" -> KycMethod.NID
        "passport" -> KycMethod.PASSPORT
        "driving_license" -> KycMethod.DRIVING_LICENSE
        "bank" -> KycMethod.BANK_STATEMENT
        "utility_bill" -> KycMethod.UTILITY_BILL
        else -> KycMethod.UNKNOWN
    },
    status = when (status) {
        "verified" -> KycStatus.VERIFIED
        "failed" -> KycStatus.FAILED
        "pending" -> KycStatus.PENDING
        else -> KycStatus.NOT_VERIFIED
    },
    attemptNumber = attemptNumber,
    submittedAt = submittedAt,
    hasRemovalRequest = hasRemovalRequest,
    hasFaceLiveness = hasFaceLiveness,
)
