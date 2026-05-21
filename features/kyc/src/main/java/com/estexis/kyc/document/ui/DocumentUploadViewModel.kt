package com.estexis.kyc.document.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.estexis.core.navigation.DocumentUploadRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentUploadViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<DocumentUploadRoute>()

    private val _state = MutableStateFlow(DocumentUploadState(type = route.type))
    val state: StateFlow<DocumentUploadState> = _state

    private val _navigationEvent = Channel<DocumentUploadNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: DocumentUploadUiEvent) {
        when (event) {
            DocumentUploadUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(DocumentUploadNavigationEvent.Back) }
            DocumentUploadUiEvent.BrowseFilesClicked ->
                _state.update { it.copy(uploadedFileName = it.placeholderFileName) }
            DocumentUploadUiEvent.DeleteFileClicked ->
                _state.update { it.copy(uploadedFileName = null) }
            DocumentUploadUiEvent.SubmitClicked -> handleSubmit()
        }
    }

    private fun handleSubmit() {
        if (_state.value.uploadedFileName == null) return
        viewModelScope.launch {
            _navigationEvent.send(DocumentUploadNavigationEvent.Submitted)
        }
    }
}
