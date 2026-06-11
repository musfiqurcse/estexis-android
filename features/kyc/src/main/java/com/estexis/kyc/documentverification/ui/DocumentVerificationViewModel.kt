package com.estexis.kyc.documentverification.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.estexis.core.common.ApiResult
import com.estexis.core.navigation.DocumentVerificationRoute
import com.estexis.core.navigation.DocumentVerificationType
import com.estexis.core.ui.util.validation.ValidateNonEmptyFieldUseCase
import com.estexis.kyc.R
import com.estexis.kyc.domain.SubmitKycParams
import com.estexis.kyc.domain.SubmitKycUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentVerificationViewModel @Inject constructor(
    private val nonEmptyFieldUseCase: ValidateNonEmptyFieldUseCase,
    private val submitKycUseCase: SubmitKycUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val route = savedStateHandle.toRoute<DocumentVerificationRoute>()

    var formState by mutableStateOf(DocumentVerificationFormState())
        private set

    private val _uiState = MutableStateFlow(DocumentVerificationUiState(
        type = route.type
    ))
    val uiState: StateFlow<DocumentVerificationUiState> = _uiState

    private val _navigationEvent = Channel<PassportVerificationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: PassportVerificationUiEvent) {
        when (event) {
            is PassportVerificationUiEvent.PassportNumberChanged -> {
                formState = formState.copy(documentNumber = event.value)
                _uiState.update { it.copy(documentNumberError = null) }
            }

            is PassportVerificationUiEvent.DateOfBirthChanged -> {
                formState = formState.copy(dateOfBirth = event.value)
                _uiState.update { it.copy(dateOfBirthError = null) }
            }

            is PassportVerificationUiEvent.ExpiryDateChanged -> {
                formState = formState.copy(expiryDate = event.value)
                _uiState.update { it.copy(expiryDateError = null) }
            }

            is PassportVerificationUiEvent.IssueDateChanged -> {
                formState = formState.copy(issueDate = event.value)
                _uiState.update { it.copy(issueDateError = null) }
            }

            is PassportVerificationUiEvent.CountryChanged -> {
                formState = formState.copy(countryOfIssue = event.value)
                _uiState.update { it.copy(countryError = null) }
            }

            PassportVerificationUiEvent.ContinueClicked -> handleContinue()
            PassportVerificationUiEvent.BackClicked -> handleBack()
            PassportVerificationUiEvent.SubmitClicked -> handleSubmit()

            is PassportVerificationUiEvent.StartCapture ->
                _uiState.update { it.copy(captureMode = event.target) }

            is PassportVerificationUiEvent.PhotoTaken -> handlePhotoTaken(event.uri)
            PassportVerificationUiEvent.CancelCapture ->
                _uiState.update { it.copy(captureMode = null) }
            PassportVerificationUiEvent.FaceVerified ->
                formState = formState.copy(faceVerified = true)
        }
    }

    private fun handleContinue() {
        // if (!isStep1Valid()) return
        _uiState.update { it.copy(currentStep = it.currentStep + 1) }
    }

    private fun handleBack() {
        val current = _uiState.value
        when {
            current.captureMode != null ->
                _uiState.update { it.copy(captureMode = null) }

            current.currentStep > 1 ->
                _uiState.update { it.copy(currentStep = it.currentStep - 1) }

            else ->
                viewModelScope.launch {
                    _navigationEvent.send(PassportVerificationNavigationEvent.Back)
                }
        }
    }

    private fun handlePhotoTaken(uri: android.net.Uri) {
        val target = _uiState.value.captureMode ?: return
        formState = when (target) {
            DocumentPhotoTarget.COVER -> formState.copy(coverPhotoUri = uri)
            DocumentPhotoTarget.DATA -> formState.copy(dataPhotoUri = uri)
        }
        _uiState.update { it.copy(captureMode = null) }
    }

    private fun handleSubmit() {
        if (formState.coverPhotoUri == null || formState.dataPhotoUri == null) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submitError = null) }
            val params = SubmitKycParams(
                id = route.submissionId,
                documentType = route.type.toApiDocumentType(),
                documentNumber = formState.documentNumber,
                dateOfBirth = formState.dateOfBirth,
                expiryDate = formState.expiryDate,
                countryOfIssue = formState.countryOfIssue,
                files = listOfNotNull(
                    formState.coverPhotoUri?.toString(),
                    formState.dataPhotoUri?.toString(),
                ),
            )
            when (val result = submitKycUseCase.invoke(params)) {
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isSubmitting = false) }
                    _navigationEvent.send(PassportVerificationNavigationEvent.Submitted)
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isSubmitting = false, submitError = result.message) }
                }
            }
        }
    }

    private fun DocumentVerificationType.toApiDocumentType(): String = when (this) {
        DocumentVerificationType.PASSPORT -> "passport"
        DocumentVerificationType.DRIVING_LICENSE -> "driving_license"
        DocumentVerificationType.NID -> "nid"
    }

    private fun isStep1Valid(): Boolean {
        val isValidPassportNumber = isValidPassportNumber()
        val isValidDOB = isValidDOB()
        val isValidExpiryDate = isValidExpiryDate()
        val isValidIssueDate = isValidIssueDate()
        val isValidCountryOfIssue = isValidCountryOfIssue()

        return isValidPassportNumber && isValidDOB &&
            isValidExpiryDate && isValidIssueDate &&
            isValidCountryOfIssue
    }

    private fun isValidPassportNumber(): Boolean {
        val validationResult = nonEmptyFieldUseCase.isEmpty(
            formState.documentNumber,
            R.string.passport_verification_screen_passport_number_needed
        )
        _uiState.update {
            it.copy(documentNumberError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidDOB(): Boolean {
        val validationResult = nonEmptyFieldUseCase.isEmpty(
            formState.dateOfBirth,
            R.string.passport_verification_screen_dob_needed
        )
        _uiState.update {
            it.copy(dateOfBirthError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidExpiryDate(): Boolean {
        val validationResult = nonEmptyFieldUseCase.isEmpty(
            formState.expiryDate,
            R.string.passport_verification_screen_exp_date_needed
        )
        _uiState.update {
            it.copy(expiryDateError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidIssueDate(): Boolean {
        val validationResult = nonEmptyFieldUseCase.isEmpty(
            formState.issueDate,
            R.string.passport_verification_screen_issue_date_needed
        )
        _uiState.update {
            it.copy(issueDateError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }

    private fun isValidCountryOfIssue(): Boolean {
        val validationResult = nonEmptyFieldUseCase.isEmpty(
            formState.countryOfIssue,
            R.string.passport_verification_screen_country_of_issue_needed
        )
        _uiState.update {
            it.copy(countryError = validationResult.errorMessage)
        }
        return validationResult.isSuccessful
    }
}
