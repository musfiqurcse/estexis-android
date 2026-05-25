package com.estexis.kyc.documentupload.ui

import com.estexis.core.navigation.DocumentUploadType

data class DocumentUploadState(
    val type: DocumentUploadType = DocumentUploadType.BANK_STATEMENT,
    val uploadedFileName: String? = null,
    val isLoading: Boolean = false,
) {
    val title: String get() = when (type) {
        DocumentUploadType.BANK_STATEMENT -> "Bank Account Statement"
        DocumentUploadType.UTILITY_BILL -> "Utility Bill"
    }

    val placeholderFileName: String get() = when (type) {
        DocumentUploadType.BANK_STATEMENT -> "bank_statement"
        DocumentUploadType.UTILITY_BILL -> "utility_bill"
    }
}
