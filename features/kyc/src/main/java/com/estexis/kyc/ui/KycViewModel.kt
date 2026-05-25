package com.estexis.kyc.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estexis.core.navigation.DocumentVerificationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KycViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(KycState())
    val state: StateFlow<KycState> = _state

    private val _navigationEvent = Channel<KycNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

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
                    DocumentVerificationType.PASSPORT
                )
                IdentificationType.NID -> triggerVerificationNavEvent(
                    DocumentVerificationType.NID
                )
                IdentificationType.DRIVING_LICENSE -> triggerVerificationNavEvent(
                    DocumentVerificationType.DRIVING_LICENSE
                )
            }

            is KycUiEvent.DocumentClicked ->
                viewModelScope.launch {
                    _navigationEvent.send(KycNavigationEvent.ToDocumentUpload(event.type))
                }
        }
    }

    private fun triggerVerificationNavEvent(type: DocumentVerificationType) {
        viewModelScope.launch {
            _navigationEvent.send(
                KycNavigationEvent.ToDocumentVerification(type)
            )
        }
    }
}
