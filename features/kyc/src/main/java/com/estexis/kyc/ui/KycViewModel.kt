package com.estexis.kyc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estexis.core.common.ApiResult
import com.estexis.core.navigation.DocumentVerificationType
import com.estexis.kyc.domain.GetKycSubmissionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KycViewModel @Inject constructor(
    private val getKycSubmissionsUseCase: GetKycSubmissionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(KycState())
    val state: StateFlow<KycState> = _state

    private val _navigationEvent = Channel<KycNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        fetchSubmissions()
    }

    private fun fetchSubmissions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getKycSubmissionsUseCase.invoke()) {
                is ApiResult.Success -> _state.update {
                    it.copy(isLoading = false, submissions = result.data)
                }
                is ApiResult.Error -> _state.update {
                    it.copy(isLoading = false, error = result.message)
                }
            }
        }
    }

    fun onEvent(event: KycUiEvent) {
        when (event) {
            KycUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(KycNavigationEvent.Back) }

            KycUiEvent.ToggleIdentification ->
                _state.update { it.copy(identificationExpanded = !it.identificationExpanded) }

            KycUiEvent.ToggleDocuments ->
                _state.update { it.copy(documentsExpanded = !it.documentsExpanded) }

            is KycUiEvent.IdentificationClicked -> when (event.type) {
                IdentificationType.PASSPORT -> triggerVerificationNavEvent(
                    DocumentVerificationType.PASSPORT, event.submissionId
                )
                IdentificationType.NID -> triggerVerificationNavEvent(
                    DocumentVerificationType.NID, event.submissionId
                )
                IdentificationType.DRIVING_LICENSE -> triggerVerificationNavEvent(
                    DocumentVerificationType.DRIVING_LICENSE, event.submissionId
                )
            }

            is KycUiEvent.DocumentClicked ->
                viewModelScope.launch {
                    _navigationEvent.send(KycNavigationEvent.ToDocumentUpload(event.type))
                }

            KycUiEvent.Retry -> fetchSubmissions()
        }
    }

    private fun triggerVerificationNavEvent(type: DocumentVerificationType, submissionId: String = "") {
        viewModelScope.launch {
            _navigationEvent.send(
                KycNavigationEvent.ToDocumentVerification(type, submissionId)
            )
        }
    }
}
