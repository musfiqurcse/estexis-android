package com.estexis.faceverification.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.estexis.core.common.ApiResult
import com.estexis.core.navigation.FaceVerificationRoute
import com.estexis.faceverification.domain.UploadFacePhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaceVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val uploadFacePhotos: UploadFacePhotosUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<FaceVerificationRoute>()

    private val _uiState = MutableStateFlow(FaceVerificationUiState())
    val uiState: StateFlow<FaceVerificationUiState> = _uiState

    private val _navigationEvent = Channel<FaceVerificationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: FaceVerificationUiEvent) {
        when (event) {
            is FaceVerificationUiEvent.PhotoCaptured ->
                _uiState.update { it.copy(capturedPhotos = it.capturedPhotos + event.uri) }

            FaceVerificationUiEvent.DoneClicked -> handleDone()

            FaceVerificationUiEvent.BackClicked ->
                viewModelScope.launch {
                    _navigationEvent.send(FaceVerificationNavigationEvent.Back)
                }
        }
    }

    private fun handleDone() {
        val photos = _uiState.value.capturedPhotos
        if (photos.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = uploadFacePhotos(route.submissionId, photos)) {
                is ApiResult.Success ->
                    _navigationEvent.send(FaceVerificationNavigationEvent.Done)
                is ApiResult.Error ->
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
            }
        }
    }
}
