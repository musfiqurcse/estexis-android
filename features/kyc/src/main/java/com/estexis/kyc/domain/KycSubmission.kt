package com.estexis.kyc.domain

import com.estexis.kyc.ui.KycStatus

enum class KycMethod {
    NID, PASSPORT, DRIVING_LICENSE, BANK_STATEMENT, UTILITY_BILL, UNKNOWN
}

data class KycSubmission(
    val id: String,
    val method: KycMethod,
    val status: KycStatus,
    val attemptNumber: Int,
    val submittedAt: String,
    val hasRemovalRequest: Boolean,
    val hasFaceLiveness: Boolean,
)
