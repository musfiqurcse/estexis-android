package com.estexis.kyc.ui

enum class KycStatus { VERIFIED, FAILED, NOT_VERIFIED, PENDING }

enum class IdentificationType {
    NID, PASSPORT, DRIVING_LICENSE
}

data class KycState(
    val identificationExpanded: Boolean = true,
    val documentsExpanded: Boolean = true,
)
