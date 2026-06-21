package com.estexis.faceverification.ui

import android.net.Uri

sealed class FaceVerificationUiEvent {
    data class PhotoCaptured(val uri: Uri) : FaceVerificationUiEvent()
    object DoneClicked : FaceVerificationUiEvent()
    object BackClicked : FaceVerificationUiEvent()
}

sealed class FaceVerificationNavigationEvent {
    object Back : FaceVerificationNavigationEvent()
    object Done : FaceVerificationNavigationEvent()
}

data class FaceVerificationUiState(
    val capturedPhotos: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val isCompleted: Boolean get() = capturedPhotos.size >= 3
    val latestPhotoUri: Uri? get() = capturedPhotos.lastOrNull()
}
