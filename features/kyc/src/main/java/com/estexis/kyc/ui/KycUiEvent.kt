package com.estexis.kyc.ui

import com.estexis.core.navigation.DocumentUploadType
import com.estexis.core.navigation.DocumentVerificationType

sealed class KycUiEvent {
    object BackClicked : KycUiEvent()
    object ToggleIdentification : KycUiEvent()
    object ToggleDocuments : KycUiEvent()
    data class IdentificationClicked(val type: IdentificationType, val submissionId: String = "") : KycUiEvent()
    data class DocumentClicked(val type: DocumentUploadType) : KycUiEvent()
    object Retry : KycUiEvent()
}

sealed class KycNavigationEvent {
    object Back : KycNavigationEvent()
    data class ToDocumentVerification(val type: DocumentVerificationType, val submissionId: String = "") : KycNavigationEvent()
    data class ToDocumentUpload(val type: DocumentUploadType) : KycNavigationEvent()
}
