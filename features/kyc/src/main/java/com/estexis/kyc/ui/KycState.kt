package com.estexis.kyc.ui

import com.estexis.kyc.domain.KycMethod
import com.estexis.kyc.domain.KycSubmission

enum class KycStatus { VERIFIED, FAILED, NOT_VERIFIED, PENDING, UNDER_REVIEW }

enum class IdentificationType {
    NID, PASSPORT, DRIVING_LICENSE
}

data class KycState(
    val identificationExpanded: Boolean = true,
    val documentsExpanded: Boolean = true,
    val isLoading: Boolean = false,
    val submissions: List<KycSubmission> = emptyList(),
    val error: String? = null,
) {
    fun statusFor(method: KycMethod): KycStatus =
        submissions.find { it.method == method }?.status ?: KycStatus.NOT_VERIFIED

    fun submissionIdFor(method: KycMethod): String =
        submissions.find { it.method == method }?.id ?: ""
}
