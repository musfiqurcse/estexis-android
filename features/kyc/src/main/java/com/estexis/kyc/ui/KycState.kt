package com.estexis.kyc.ui

import com.estexis.kyc.domain.KycMethod
import com.estexis.kyc.domain.KycSubmission

enum class KycStatus { VERIFIED, FAILED, NOT_VERIFIED, PENDING }

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
    val identificationSubmissions get() = submissions.filter {
        it.method in listOf(KycMethod.NID, KycMethod.PASSPORT, KycMethod.DRIVING_LICENSE)
    }
    val documentSubmissions get() = submissions.filter {
        it.method in listOf(KycMethod.BANK_STATEMENT, KycMethod.UTILITY_BILL)
    }
}
