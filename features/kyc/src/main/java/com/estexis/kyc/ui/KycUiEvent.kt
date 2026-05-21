package com.estexis.kyc.ui

sealed class KycUiEvent {
    object BackClicked : KycUiEvent()
    object ToggleIdentification : KycUiEvent()
    object ToggleDocuments : KycUiEvent()
    data class IdentificationClicked(val type: IdentificationType) : KycUiEvent()
    data class DocumentClicked(val type: DocumentType) : KycUiEvent()
}

sealed class KycNavigationEvent {
    object Back : KycNavigationEvent()
}
