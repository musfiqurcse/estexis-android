package com.estexis.kyc.documentverification.ui

import com.estexis.core.navigation.DocumentVerificationType
import com.estexis.core.ui.util.UiText
import com.estexis.kyc.R

enum class DocumentPhotoTarget { COVER, DATA }

data class DocumentVerificationFormState(
    val documentNumber: String = "",
    val dateOfBirth: String = "",
    val expiryDate: String = "",
    val issueDate: String = "",
    val countryOfIssue: String = "",
    val coverPhotoCaptured: Boolean = false,
    val dataPhotoCaptured: Boolean = false,
)

data class DocumentVerificationUiState(
    val type: DocumentVerificationType = DocumentVerificationType.PASSPORT,
    val currentStep: Int = 1,
    val captureMode: DocumentPhotoTarget? = null,
    val isLoading: Boolean = false,
    val documentNumberError: UiText? = null,
    val dateOfBirthError: UiText? = null,
    val expiryDateError: UiText? = null,
    val issueDateError: UiText? = null,
    val countryError: UiText? = null,
) {
    val isCaptureMode: Boolean get() = captureMode != null
    val canSubmit: Boolean get() = currentStep == 2 && !isCaptureMode

    val title: Int get() = when (type) {
        DocumentVerificationType.PASSPORT ->
            R.string.passport_verification_title
        DocumentVerificationType.DRIVING_LICENSE ->
            R.string.driving_license_verification_title
        DocumentVerificationType.NID ->
            R.string.personal_identity_verification_title
    }

    val documentNumberLabel: Int get() = when (type) {
        DocumentVerificationType.PASSPORT ->
            R.string.document_verification_screen_passport_number
        DocumentVerificationType.DRIVING_LICENSE ->
            R.string.document_verification_screen_driving_license_number
        DocumentVerificationType.NID ->
            R.string.document_verification_screen_document_number
    }

    val documentNumberPlaceholder: Int get() = when (type) {
        DocumentVerificationType.PASSPORT ->
            R.string.document_verification_screen_enter_passport_number
        DocumentVerificationType.DRIVING_LICENSE ->
            R.string.document_verification_screen_enter_driving_license_number
        DocumentVerificationType.NID ->
            R.string.document_verification_screen_enter_document_number
    }
}
