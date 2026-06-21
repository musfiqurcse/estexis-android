package com.estexis.kyc.documentverification.ui

import android.net.Uri
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
    val coverPhotoUri: Uri? = null,
    val dataPhotoUri: Uri? = null,
)

data class DocumentVerificationUiState(
    val type: DocumentVerificationType = DocumentVerificationType.PASSPORT,
    val currentStep: Int = 1,
    val captureMode: DocumentPhotoTarget? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val submitError: String? = null,
    val documentNumberError: UiText? = null,
    val dateOfBirthError: UiText? = null,
    val expiryDateError: UiText? = null,
    val issueDateError: UiText? = null,
    val countryError: UiText? = null,
) {
    val isCaptureMode: Boolean get() = captureMode != null
    val totalSteps: Int get() = 2
    val isLastStep: Boolean get() = currentStep == totalSteps

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

    val documentFrontPageLabel: Int get() = when (type) {
        DocumentVerificationType.PASSPORT,
        DocumentVerificationType.DRIVING_LICENSE ->
            R.string.document_verification_screen_cover_page
        DocumentVerificationType.NID ->
            R.string.document_verification_screen_front_page
    }
    val documentBackPageLabel: Int get() = when (type) {
        DocumentVerificationType.PASSPORT,
        DocumentVerificationType.DRIVING_LICENSE ->
            R.string.document_verification_screen_data_page
        DocumentVerificationType.NID ->
            R.string.document_verification_screen_back_page
    }
}
