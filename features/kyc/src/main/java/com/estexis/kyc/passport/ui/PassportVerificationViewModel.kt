package com.estexis.kyc.passport.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassportVerificationViewModel @Inject constructor() : ViewModel() {

    var formState by mutableStateOf(PassportVerificationFormState())
        private set

    private val _uiState = MutableStateFlow(PassportVerificationUiState())
    val uiState: StateFlow<PassportVerificationUiState> = _uiState

    private val _navigationEvent = Channel<PassportVerificationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: PassportVerificationUiEvent) {
        when (event) {
            is PassportVerificationUiEvent.PassportNumberChanged -> {
                formState = formState.copy(passportNumber = event.value)
                _uiState.update { it.copy(passportNumberError = null) }
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
            PassportVerificationUiEvent.TakePhotoClicked -> handleTakePhoto()
            PassportVerificationUiEvent.CancelCapture ->
                _uiState.update { it.copy(captureMode = null) }
        }
    }

    private fun handleContinue() {
        //if (!isStep1Valid()) return
        _uiState.update { it.copy(currentStep = 2) }
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

    private fun handleTakePhoto() {
        val target = _uiState.value.captureMode ?: return
        formState = when (target) {
            PassportPhotoTarget.COVER -> formState.copy(coverPhotoCaptured = true)
            PassportPhotoTarget.DATA -> formState.copy(dataPhotoCaptured = true)
        }
        _uiState.update { it.copy(captureMode = null) }
    }

    private fun handleSubmit() {
        if (!formState.coverPhotoCaptured || !formState.dataPhotoCaptured) return
        viewModelScope.launch {
            _navigationEvent.send(PassportVerificationNavigationEvent.Submitted)
        }
    }

    private fun isStep1Valid(): Boolean {
        var valid = true
        _uiState.update {
            it.copy(
                passportNumberError = if (formState.passportNumber.isBlank()) {
                    valid = false; "Passport number is required"
                } else null,
                dateOfBirthError = if (formState.dateOfBirth.isBlank()) {
                    valid = false; "Date of birth is required"
                } else null,
                expiryDateError = if (formState.expiryDate.isBlank()) {
                    valid = false; "Expiry date is required"
                } else null,
                issueDateError = if (formState.issueDate.isBlank()) {
                    valid = false; "Issue date is required"
                } else null,
                countryError = if (formState.countryOfIssue.isBlank()) {
                    valid = false; "Country is required"
                } else null,
            )
        }
        return valid
    }
}
