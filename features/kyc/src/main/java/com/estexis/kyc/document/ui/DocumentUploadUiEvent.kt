package com.estexis.kyc.document.ui

sealed class DocumentUploadUiEvent {
    object BackClicked : DocumentUploadUiEvent()
    object BrowseFilesClicked : DocumentUploadUiEvent()
    object DeleteFileClicked : DocumentUploadUiEvent()
    object SubmitClicked : DocumentUploadUiEvent()
}

sealed class DocumentUploadNavigationEvent {
    object Back : DocumentUploadNavigationEvent()
    object Submitted : DocumentUploadNavigationEvent()
}
