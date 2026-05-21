package com.estexis.kyc.ui

enum class KycStatus { VERIFIED, FAILED, NOT_VERIFIED, PENDING }

enum class IdentificationType {
    NID, PASSPORT, DRIVING_LICENSE
}

enum class DocumentType {
    BANK_STATEMENT, UTILITY_BILL
}

data class IdentificationItem(
    val type: IdentificationType,
    val title: String,
    val status: KycStatus,
)

data class DocumentItem(
    val type: DocumentType,
    val title: String,
    val status: KycStatus,
)

data class KycState(
    val identificationExpanded: Boolean = true,
    val documentsExpanded: Boolean = true,
    val identificationItems: List<IdentificationItem> = listOf(
        IdentificationItem(IdentificationType.NID, "NID Verification", KycStatus.FAILED),
        IdentificationItem(IdentificationType.PASSPORT, "Passport Verification", KycStatus.NOT_VERIFIED),
        IdentificationItem(IdentificationType.DRIVING_LICENSE, "Driving License Verification", KycStatus.PENDING),
    ),
    val documentItems: List<DocumentItem> = listOf(
        DocumentItem(DocumentType.BANK_STATEMENT, "Bank Account Statement", KycStatus.VERIFIED),
        DocumentItem(DocumentType.UTILITY_BILL, "Utility Bill", KycStatus.NOT_VERIFIED),
    ),
)
